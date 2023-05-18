package com.lastone.apiserver.exception.mypage;

import com.lastone.core.common.response.ErrorCode;

public class MemberNotFountException extends MyPageException {
    public MemberNotFountException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}