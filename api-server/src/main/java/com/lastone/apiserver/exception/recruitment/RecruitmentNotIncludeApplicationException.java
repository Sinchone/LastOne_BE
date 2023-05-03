package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.response.ErrorCode;

public class RecruitmentNotIncludeApplicationException extends RecruitmentException {

    public RecruitmentNotIncludeApplicationException() {
        super(ErrorCode.RECRUITMENT_NOT_INCLUDE_APPLICATION);
    }
}
