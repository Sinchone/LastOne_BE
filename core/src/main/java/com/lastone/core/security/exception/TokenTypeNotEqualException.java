package com.lastone.core.security.exception;

import com.lastone.core.common.response.ErrorCode;

public class TokenTypeNotEqualException extends CustomTokenException{
    public TokenTypeNotEqualException(ErrorCode errorCode) {
        super(errorCode);
    }
}
