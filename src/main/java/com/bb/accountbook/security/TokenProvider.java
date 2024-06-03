package com.bb.accountbook.security;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.domain.user.dto.AdditionalPayloadDto;
import com.bb.accountbook.domain.user.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;

@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;
    private Key key;
    private final String iss;


    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-time}") long accessTokenExpirationTimeInSecond,
            @Value("${jwt.refresh-token-expiration-time}") long refreshTokenExpirationTimeInSecond,
            @Value("${jwt.iss}") String iss) {
        this.secret = secret;
        this.accessTokenExpirationTime = accessTokenExpirationTimeInSecond * 1000;
        this.refreshTokenExpirationTime = refreshTokenExpirationTimeInSecond * 1000;
        this.iss = iss;
    }

    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date accessTokenExpiration = new Date(now + this.accessTokenExpirationTime);
        Date refreshTokenExpiration = new Date(now + this.refreshTokenExpirationTime);

        AdditionalPayloadDto additionalPayloadDto = (AdditionalPayloadDto) authentication.getDetails();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(Claims.SUBJECT, additionalPayloadDto.getUid())
                .claim(Claims.ISSUER, iss)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenExpiration)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(Claims.SUBJECT, additionalPayloadDto.getUid())
                .claim(Claims.ISSUER, iss)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(refreshTokenExpiration)
                .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public void validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        }
        catch(SecurityException | MalformedJwtException e) {
            throw new GlobalException(ERR_AUTH_004);
        }
        catch(ExpiredJwtException e) {
            throw new GlobalException(ERR_AUTH_005);
        }
        catch(UnsupportedJwtException e) {
            throw new GlobalException(ERR_AUTH_006);
        }
        catch(IllegalArgumentException e) {
            throw new GlobalException(ERR_AUTH_007);
        }
    }
}
