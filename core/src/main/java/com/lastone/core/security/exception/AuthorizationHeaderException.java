package com.lastone.core.security.exception;


import com.lastone.core.common.response.ErrorCode;

public class AuthorizationHeaderException extends SecurityException{

    public AuthorizationHeaderException() {
        super(ErrorCode.AUTHORIZATION_NOT_FOUND);
    }
}
