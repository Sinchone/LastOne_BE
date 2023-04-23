package com.lastone.core.security.exception;

import com.lastone.core.common.response.ErrorCode;

public class AccessTokenExpiredException extends CustomTokenException {

    public AccessTokenExpiredException() {
        super(ErrorCode.ACCESS_TOKEN_EXPIRED_EXCEPTION);
    }
}
