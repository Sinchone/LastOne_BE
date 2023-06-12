package com.lastone.apiserver.exception.global;

import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class ImgTypeNotSupportedException extends LastOneException {

    public ImgTypeNotSupportedException() {
        super(ErrorCode.IMG_NOT_SUPPORTED);
    }
}
