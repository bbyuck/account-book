package com.bb.accountbook.common.exception;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.codes.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch(GlobalException e) {
            setErrorResponse(request, response, e);
        } catch(Exception e) {
            setErrorResponse(request, response, e);
        }
    }

    private static void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException{
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        response.getWriter().write(new ApiResponse<>(ErrorCode.ERR_SYS_000.getValue()).serialize());
    }
    private static void setErrorResponse(HttpServletRequest request, HttpServletResponse response, GlobalException e) throws IOException {
        ErrorCode errorCode = e.getErrorCode();

        response.setStatus(errorCode.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        response.getWriter().write(new ApiResponse<>(errorCode.getValue()).serialize());
    }
}
