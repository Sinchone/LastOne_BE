package com.lastone.chat.persistence;

import lombok.Getter;

@Getter
public enum MessageColumn {
    COLLECTION_NAME("messages"),
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
