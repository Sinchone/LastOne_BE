package com.lastone.core.security.exception;


import com.lastone.core.common.response.ErrorCode;

public class AlreadyLogoutException extends SecurityException{

    public AlreadyLogoutException() {
        super(ErrorCode.ALREADY_LOGOUT_TOKEN);
    }
}
