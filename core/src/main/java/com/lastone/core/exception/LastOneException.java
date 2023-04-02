package com.lastone.core.exception;

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
