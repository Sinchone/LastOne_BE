package com.lastone.core.oauth2;

import com.lastone.core.common.response.ErrorCode;

public class AlreadyUsedCodeException extends Oauth2Exception {
    public AlreadyUsedCodeException() {
        super(ErrorCode.OAUTH2_ALREADY_USED_CODE);
    }
}
