package com.lastone.core.security.exception;

import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class CustomTokenException extends LastOneException {
    public CustomTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
