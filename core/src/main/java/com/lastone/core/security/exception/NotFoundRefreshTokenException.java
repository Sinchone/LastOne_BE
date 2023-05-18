package com.lastone.core.security.exception;

import com.lastone.core.common.response.ErrorCode;

public class NotFoundRefreshTokenException extends SecurityException{

    public NotFoundRefreshTokenException() {
        super(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }
}
