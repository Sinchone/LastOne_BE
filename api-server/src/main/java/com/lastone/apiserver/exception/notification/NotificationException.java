package com.lastone.apiserver.exception.notification;

import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.ErrorCode;

public class NotificationException extends LastOneException {

    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}