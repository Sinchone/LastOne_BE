package com.lastone.apiserver.exception;

import com.lastone.core.common.response.ErrorCode;

public class MemberAlreadyExistException extends MyPageException {
    public MemberAlreadyExistException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public MemberAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
