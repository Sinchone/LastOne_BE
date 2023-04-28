package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.ErrorCode;

public class ApplicantIsEqualToWriterException extends ApplicationException{
    public ApplicantIsEqualToWriterException() {
        super(ErrorCode.APPLICANT_EQUAL_TO_WRITER);
    }
}
