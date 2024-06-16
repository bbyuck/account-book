package com.bb.accountbook.common.exception;

import com.bb.accountbook.common.model.codes.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalCheckedException extends Exception {
    private ErrorCode errorCode;

    public GlobalCheckedException(ErrorCode errorCode) {
        super(errorCode.getValue());
        this.errorCode = errorCode;
    }

    public GlobalCheckedException(String message) {
        super(message);
    }

    public GlobalCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalCheckedException(Throwable cause) {
        super(cause);
    }

    protected GlobalCheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
