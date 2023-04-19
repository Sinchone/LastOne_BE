package com.lastone.apiserver.exception.oauth2;

import com.lastone.core.common.response.ErrorCode;

public class Oauth2DefaultException extends Oauth2Exception{
    public Oauth2DefaultException() {
        super(ErrorCode.OAUTH2_DEFAULT_EXCEPTION);
    }
}