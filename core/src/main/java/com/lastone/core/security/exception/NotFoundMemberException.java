package com.lastone.core.security.exception;

import com.lastone.core.exception.ErrorCode;

public class NotFoundMemberException extends SecurityException{
    public NotFoundMemberException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
