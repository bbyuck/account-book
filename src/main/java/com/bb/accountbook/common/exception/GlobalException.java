package com.bb.accountbook.common.exception;

import com.bb.accountbook.common.model.codes.CommonCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private CommonCode commonCode;

    public GlobalException(CommonCode commonCode) {
        super(commonCode.getValue());
        this.commonCode = commonCode;
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    protected GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
