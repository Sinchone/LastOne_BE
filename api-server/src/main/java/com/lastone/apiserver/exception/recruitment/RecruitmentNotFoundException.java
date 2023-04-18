package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.response.ErrorCode;

public class RecruitmentNotFoundException extends RecruitmentException{
    public RecruitmentNotFoundException() {
        super(ErrorCode.RECRUITMENT_NOT_FOUND);
    }
}
