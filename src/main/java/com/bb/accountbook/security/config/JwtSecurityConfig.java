package com.bb.accountbook.security.config;

import com.bb.accountbook.security.TokenProvider;
import com.bb.accountbook.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void init(HttpSecurity http) throws Exception {
        System.out.println("JwtSecurityConfig.init() ========================");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("JwtSecurityConfig.configure() ========================");

        http.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
