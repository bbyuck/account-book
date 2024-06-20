package com.bb.accountbook.security.filter;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.security.TokenProvider;
import com.bb.accountbook.security.config.CustomSecurityProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
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

import static com.bb.accountbook.common.model.codes.ErrorCode.*;
import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_AUTH_007;

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
            }
            catch(SecurityException | MalformedJwtException e) {
                log.debug("{} : {}", ERR_AUTH_004.getValue(), requestURI);
                request.setAttribute("errorCode", ERR_AUTH_004);
            }
            catch(ExpiredJwtException e) {
                log.debug("{} : {}", ERR_AUTH_005.getValue(), requestURI);
                request.setAttribute("errorCode",ERR_AUTH_005);
            }
            catch(UnsupportedJwtException e) {
                log.debug("{} : {}", ERR_AUTH_006.getValue(), requestURI);
                request.setAttribute("errorCode", ERR_AUTH_006);
            }
            catch(IllegalArgumentException e) {
                log.debug("{} : {}", ERR_AUTH_007.getValue(), requestURI);
                request.setAttribute("errorCode", ERR_AUTH_007);
            }
        }

        chain.doFilter(request, response);
    }

}
