package com.lastone.chat.exception;

import com.lastone.core.exception.ErrorCode;
import com.lastone.core.exception.LastOneException;

public class ChatException extends LastOneException {
    public ChatException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ChatException(ErrorCode errorCode) {
        super(errorCode);
    }
}
