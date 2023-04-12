package com.lastone.core.security.exception;

import com.lastone.core.exception.ErrorCode;
import com.lastone.core.exception.LastOneException;

public class SecurityException extends LastOneException {

    public SecurityException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
