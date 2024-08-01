package com.bb.accountbook.security;

import com.bb.accountbook.domain.user.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String AUTHORIZATION_HEADER = "Authorization";
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

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(Claims.ISSUER, iss)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenExpiration)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(Claims.ISSUER, iss)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(refreshTokenExpiration)
                .compact();

        // 해당 객체를 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

    public LocalDateTime getExpiration(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public void validate(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);

        if (StringUtils.hasText(refreshToken)) {
            return refreshToken;
        }

        return null;
    }
}
