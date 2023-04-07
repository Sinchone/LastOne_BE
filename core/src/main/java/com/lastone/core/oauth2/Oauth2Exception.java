package com.lastone.core.oauth2;

import com.lastone.core.exception.ErrorCode;
import com.lastone.core.exception.LastOneException;

public class Oauth2Exception extends LastOneException {
    public Oauth2Exception(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public Oauth2Exception(ErrorCode errorCode) {
        super(errorCode);
    }
}
