package com.lastone.core.security.exception;


import com.lastone.core.common.response.ErrorCode;

public class AlreadyLogoutException extends SecurityException{
    public AlreadyLogoutException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AlreadyLogoutException(ErrorCode errorCode) {
        super(errorCode);
    }
}
