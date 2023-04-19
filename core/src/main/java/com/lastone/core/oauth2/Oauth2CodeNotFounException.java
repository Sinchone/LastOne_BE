package com.lastone.core.oauth2;

import com.lastone.core.common.response.ErrorCode;

public class Oauth2CodeNotFounException extends Oauth2Exception{
    public Oauth2CodeNotFounException() {
        super(ErrorCode.OAUTH2_CODE_NOT_FOUND);
    }
}
