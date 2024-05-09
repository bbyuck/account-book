package com.bb.accountbook.common.exception;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.codes.CommonCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.bb.accountbook.common.model.codes.CommonCode.*;


@Slf4j
@RestControllerAdvice
public class ServletExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ApiResponse(ERR_SYS_000.getValue());
    }

    @ExceptionHandler(GlobalException.class)
    public ApiResponse handleGlobalException(GlobalException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ApiResponse(e.getCommonCode().getValue());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ApiResponse(ERR_SYS_001.getValue());
    }
}
