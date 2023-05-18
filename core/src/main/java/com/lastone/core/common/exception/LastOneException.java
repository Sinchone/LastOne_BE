package com.lastone.core.common.exception;

import com.lastone.core.common.response.ErrorCode;

public class LastOneException extends RuntimeException {
    private ErrorCode errorCode;

    public LastOneException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public LastOneException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
