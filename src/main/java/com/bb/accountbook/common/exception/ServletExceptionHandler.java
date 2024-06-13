package com.bb.accountbook.common.exception;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.codes.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;


@Slf4j
@RestControllerAdvice
public class ServletExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ApiResponse(ERR_SYS_000);
    }

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(GlobalException e, HttpServletResponse response) {
        response.setStatus(e.getErrorCode().getHttpStatus());
        return new ApiResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        boolean isPropertyExist = ValidationMessage.map.containsKey(e.getBindingResult().getFieldError().getDefaultMessage());
        String message = isPropertyExist
                ? ValidationMessage.map.get(e.getBindingResult().getFieldError().getDefaultMessage()).getValue()
                : e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = isPropertyExist
                ? ValidationMessage.map.get(e.getBindingResult().getFieldError().getDefaultMessage())
                : ERR_SYS_003;

        return new ApiResponse<>(errorCode, message);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse<?> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ApiResponse(ERR_SYS_002);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse<?> handleNoResourceFoundException(NoResourceFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ApiResponse(ERR_SYS_003);
    }
}
