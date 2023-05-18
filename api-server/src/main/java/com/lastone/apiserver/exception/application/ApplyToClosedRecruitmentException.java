package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class ApplyToClosedRecruitmentException extends ApplicationException {
    public ApplyToClosedRecruitmentException() {
        super(ErrorCode.APPLY_TO_CLOSED_RECRUITMENT);
    }
}
