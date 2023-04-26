package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class AlreadyAppliedException extends ApplicationException {
    public AlreadyAppliedException() {
        super(ErrorCode.ALREADY_APPLIED_RECRUITMENT);
    }
}
