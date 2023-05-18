package com.lastone.apiserver.exception.mypage;

import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.common.exception.LastOneException;

public class MyPageException extends LastOneException {
    public MyPageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
