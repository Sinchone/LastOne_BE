package com.lastone.core.security.exception;


import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class SecurityException extends LastOneException {

    public SecurityException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
