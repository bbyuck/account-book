package com.bb.accountbook.common.advice;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.OnSuccess;
import com.bb.accountbook.common.model.codes.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;


@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse) {
            return body;
        }

        OnSuccess onSuccess = returnType.getMethodAnnotation(OnSuccess.class);
        return onSuccess == null
                ? new ApiResponse<>(body)
                : new ApiResponse<>(body, onSuccess.value());
    }

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


        boolean isPropertyExist = Validation.map.containsKey(e.getBindingResult().getFieldError().getDefaultMessage());
        String message = isPropertyExist
                ? Validation.map.get(e.getBindingResult().getFieldError().getDefaultMessage()).getValue()
                : e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = isPropertyExist
                ? Validation.map.get(e.getBindingResult().getFieldError().getDefaultMessage())
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
