package com.bb.accountbook.security.filter;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.security.TokenProvider;
import com.bb.accountbook.security.config.CustomSecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;
    private final CustomSecurityProperties customSecurityProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = tokenProvider.resolveAccessToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (!customSecurityProperties.getWhiteList().contains(requestURI)
                && !requestURI.startsWith("/h2-console")) {
            try {
                tokenProvider.validate(jwt);

                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. URI : {}", authentication.getName(), requestURI);
            } catch (GlobalException e) {
                log.error("{} : {}", e.getErrorCode().getValue(), requestURI);
                request.setAttribute("errorCode", e.getErrorCode());
            }
        }

        chain.doFilter(request, response);
    }

}
