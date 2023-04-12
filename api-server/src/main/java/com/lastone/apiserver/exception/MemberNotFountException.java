package com.lastone.apiserver.exception;

import com.lastone.core.common.response.ErrorCode;

public class MemberNotFountException extends MyPageException {
    public MemberNotFountException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public MemberNotFountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
