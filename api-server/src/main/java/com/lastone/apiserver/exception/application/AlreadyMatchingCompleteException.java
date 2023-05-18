package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class AlreadyMatchingCompleteException extends ApplicationException {
    public AlreadyMatchingCompleteException() {
        super(ErrorCode.ALREADY_MATCHING_COMPLETE);
    }
}
