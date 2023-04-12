package com.lastone.core.security.exception;

import com.lastone.core.exception.ErrorCode;

public class NotFoundRefreshTokenException extends SecurityException{

    public NotFoundRefreshTokenException() {
        super(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }
}
