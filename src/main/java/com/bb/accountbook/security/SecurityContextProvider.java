package com.bb.accountbook.security;


import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

@Slf4j
@Component
public class SecurityContextProvider implements InitializingBean {

    private final String secret;
    private Key key;

    public SecurityContextProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getCurrentEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }

        String email = null;
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            email = springSecurityUser.getUsername();
        }
        else if(authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
        }


        if (email == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }

        return email;
    }

    public Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }


        String token = (String) authentication.getCredentials();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        return Long.valueOf(body.get(Claims.ISSUER).toString());
    }

}
