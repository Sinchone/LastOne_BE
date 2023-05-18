package com.lastone.apiserver.exception.notification;

import com.lastone.core.common.response.ErrorCode;

public class NotificationNotFoundException extends NotificationException {
    public NotificationNotFoundException() {
        super(ErrorCode.NOTIFICATION_NOT_FOUND);
    }
}
