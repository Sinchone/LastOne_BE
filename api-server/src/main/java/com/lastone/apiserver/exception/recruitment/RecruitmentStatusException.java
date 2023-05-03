package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.response.ErrorCode;

public class RecruitmentStatusException extends RecruitmentException {

    public RecruitmentStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}