package com.bb.accountbook.common.model;

import com.bb.accountbook.common.util.DateTimeUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ApiResponse<T> {

    private T data;
    private String message;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtil.FORMAT));

    public ApiResponse(T data) {
        this.data = data;
        this.message = "정상 처리 되었습니다.";
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

}
