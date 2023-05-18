package com.lastone.apiserver.exception.notification;

import com.lastone.core.common.response.ErrorCode;

public class NotificationAlreadyReadException extends NotificationException {

    public NotificationAlreadyReadException() {
        super(ErrorCode.NOTIFICATION_ALREADY_READ);
    }
}