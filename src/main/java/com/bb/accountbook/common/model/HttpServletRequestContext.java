package com.bb.accountbook.common.model;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpServletRequestContext {

    private final HttpServletRequest request;


    public Long getUserId() {
        return Long.valueOf(request.getHeader("userId"));
    }
}
