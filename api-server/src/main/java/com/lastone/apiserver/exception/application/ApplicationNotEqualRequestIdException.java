package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class ApplicationNotEqualRequestIdException extends ApplicationException {
    public ApplicationNotEqualRequestIdException() {
        super(ErrorCode.APPLICATION_NOT_EQUAL_REQUEST_ID);
    }
}
