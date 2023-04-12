package com.lastone.core.security.exception;


import com.lastone.core.common.response.ErrorCode;

public class AuthorizationHeaderException extends SecurityException{

    public AuthorizationHeaderException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AuthorizationHeaderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
