package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class ApplicationStatusInCorrectException extends ApplicationException {
    public ApplicationStatusInCorrectException(ErrorCode errorCode) {
        super(errorCode);
    }
}
