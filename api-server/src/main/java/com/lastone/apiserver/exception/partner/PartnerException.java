package com.lastone.apiserver.exception.partner;

import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class PartnerException extends LastOneException {
    public PartnerException(ErrorCode errorCode) {
        super(errorCode);
    }
}