package com.lastone.apiserver.exception.recruitment;

import com.lastone.core.common.response.ErrorCode;

public class RecruitmentImgCountException extends RecruitmentException{
    public RecruitmentImgCountException() {
        super(ErrorCode.RECRUITMENT_IMG_COUNT);
    }
}
