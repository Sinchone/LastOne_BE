package com.lastone.apiserver.exception.notification;

import com.lastone.core.common.response.ErrorCode;

public class NotificationUnauthorizedException extends NotificationException {

    public NotificationUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}