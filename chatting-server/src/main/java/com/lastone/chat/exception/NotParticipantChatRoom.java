package com.lastone.chat.exception;

import com.lastone.core.common.response.ErrorCode;

public class NotParticipantChatRoom extends ChatException {

    public NotParticipantChatRoom(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotParticipantChatRoom(ErrorCode errorCode) {
        super(errorCode);
    }
    public NotParticipantChatRoom() {
        super(ErrorCode.NOT_CHAT_PARTICIPANT);
    }
}
