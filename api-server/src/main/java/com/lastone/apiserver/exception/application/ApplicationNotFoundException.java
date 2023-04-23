package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class ApplicationNotFoundException extends ApplicationException{

    public ApplicationNotFoundException() {
        super(ErrorCode.APPLICATION_NOT_FOUND);
    }
}
