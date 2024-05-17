package com.bb.accountbook.security;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.domain.user.dto.AdditionalPayloadDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    public static final String AUTH_EXCEPTION = "auth-exception";
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenExpirationTime;
    private Key key;
    private final String iss;


    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-expiration-time}") long tokenExpirationTimeInSecond,
            @Value("${jwt.iss}") String iss) {
        this.secret = secret;
        this.tokenExpirationTime = tokenExpirationTimeInSecond * 1000;
        this.iss = iss;
    }

    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date validity = new Date(now + this.tokenExpirationTime);

        AdditionalPayloadDto additionalPayloadDto = (AdditionalPayloadDto) authentication.getDetails();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(Claims.SUBJECT, additionalPayloadDto.getUid())
                .claim(Claims.ISSUER, iss)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
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
            log.error(ERR_AUTH_004.getValue());
            throw new GlobalException(ERR_AUTH_004);
        }
        catch(ExpiredJwtException e) {
            log.error(ERR_AUTH_005.getValue());
            throw new GlobalException(ERR_AUTH_005);
        }
        catch(UnsupportedJwtException e) {
            log.error(ERR_AUTH_006.getValue());
            throw new GlobalException(ERR_AUTH_006);
        }
        catch(IllegalArgumentException e) {
            log.error(ERR_AUTH_007.getValue());
            throw new GlobalException(ERR_AUTH_007);
        }
    }
}
