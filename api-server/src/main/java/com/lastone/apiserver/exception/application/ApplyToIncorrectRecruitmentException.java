package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class ApplyToIncorrectRecruitmentException extends ApplicationException {

    public ApplyToIncorrectRecruitmentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
