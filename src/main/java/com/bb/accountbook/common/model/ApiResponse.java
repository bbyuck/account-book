package com.bb.accountbook.common.model;

import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.SuccessCode;
import com.bb.accountbook.common.util.DateTimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ApiResponse<T> {

    private T data;
    private String message;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtil.FORMAT));
    private static ObjectMapper objectMapper = new ObjectMapper();
    private boolean error;
    private String code;

    public ApiResponse(T data) {
        this.data = data;
        this.message = "정상 처리 되었습니다.";
    }

    public ApiResponse(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.error = true;
        this.message = errorCode.getValue();
    }

    public ApiResponse(ErrorCode errorCode, String message) {
        this.code = errorCode.name();
        this.error = true;
        this.message = message;
    }

    public ApiResponse(SuccessCode successCode) {
        this.code = successCode.name();
        this.error = false;
        this.message = successCode.getValue();
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiResponse(T data, SuccessCode successCode) {
        this(data, successCode.getValue());
        this.code = successCode.name();
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public String serialize() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

}