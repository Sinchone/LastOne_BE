package com.lastone.core.oauth2;

import com.lastone.core.exception.ErrorCode;

public class Oauth2RegisterIdException extends Oauth2Exception{

    public Oauth2RegisterIdException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public Oauth2RegisterIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
