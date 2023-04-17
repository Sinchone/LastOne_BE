package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class RecruitmentException extends LastOneException {

    public RecruitmentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
