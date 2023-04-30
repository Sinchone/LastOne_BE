package com.lastone.chat.exception;

import com.lastone.core.common.response.ErrorCode;

public class CannotFoundChatMember extends ChatException {

    public CannotFoundChatMember(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CannotFoundChatMember(ErrorCode errorCode) {
        super(errorCode);
    }
    public CannotFoundChatMember() {
        super(ErrorCode.NOT_CHAT_USER);
    }
}
