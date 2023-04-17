package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.response.ErrorCode;

public class IncorrectWriterException extends RecruitmentException{
    public IncorrectWriterException() {
        super(ErrorCode.INCORRECT_WRITER);
    }
}
