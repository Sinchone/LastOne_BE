package com.lastone.core.security.exception;

import com.lastone.core.exception.ErrorCode;

public class AlreadyLogoutException extends SecurityException{
    public AlreadyLogoutException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AlreadyLogoutException(ErrorCode errorCode) {
        super(errorCode);
    }
}
