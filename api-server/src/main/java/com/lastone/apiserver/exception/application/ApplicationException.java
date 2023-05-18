package com.lastone.apiserver.exception.application;

import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class ApplicationException extends LastOneException {
    public ApplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
