package com.lastone.chat.exception;

import com.lastone.core.common.response.ErrorCode;

public class CannotFountChatRoom extends ChatException {

    public CannotFountChatRoom(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CannotFountChatRoom(ErrorCode errorCode) {
        super(errorCode);
    }
    public CannotFountChatRoom() {
        super(ErrorCode.NOT_FOUNT_ROOM);
    }
}
