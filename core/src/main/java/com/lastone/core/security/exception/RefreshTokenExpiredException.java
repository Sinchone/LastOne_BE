package com.lastone.core.security.exception;

import com.lastone.core.common.response.ErrorCode;

public class RefreshTokenExpiredException extends CustomTokenException {
    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_EXPIRED_EXCEPTION);
    }
}
