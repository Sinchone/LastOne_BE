package com.lastone.core.security.exception;


import com.lastone.core.common.response.ErrorCode;

public class NotFoundMemberException extends SecurityException{

    public NotFoundMemberException() {
        super(ErrorCode.MEMBER_NOT_FOUND_IN_TOKEN);
    }
}
