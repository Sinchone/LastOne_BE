package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.response.ErrorCode;

public class RecruitmentAlreadyDeleteException extends RecruitmentException {

    public RecruitmentAlreadyDeleteException() {
        super(ErrorCode.RECRUITMENT_ALREADY_DELETE);
    }
}