package com.lastone.chat.dto.chatroom;

import lombok.Getter;

@Getter
public enum MessageColumn {
    ID("id"),
    SENDERID("senderId"),
    ROOMID("roomId"),
    RECEIVERID("receiverId"),
    CONTENT("content"),
    ISREAD("isRead"),
    CREATEDAT("createdAt")
    ;

    private String word;
    MessageColumn(String word) {
        this.word = word;
    }
}
