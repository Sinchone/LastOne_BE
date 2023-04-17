package com.lastone.apiserver.exception.mypage;

import com.lastone.core.common.response.ErrorCode;

public class MemberAlreadyExistException extends MyPageException {

    public MemberAlreadyExistException() {
        super(ErrorCode.MEMBER_ALREADY_EXIST);
    }
}