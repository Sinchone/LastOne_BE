package com.lastone.apiserver.exception.oauth2;

import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.common.exception.LastOneException;

public class Oauth2Exception extends LastOneException {
    public Oauth2Exception(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public Oauth2Exception(ErrorCode errorCode) {
        super(errorCode);
    }
}
