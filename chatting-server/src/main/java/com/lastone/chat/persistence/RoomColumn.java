package com.lastone.chat.persistence;


import lombok.Getter;

@Getter
public enum RoomColumn {
    COLLECTION_NAME("chat_room"),
    ID("_id"),
    PARTICIPATIONS("participations"),
    STATUS("status"),
    CREATEDAT("createdAt"),
    UPDATEDAT("updatedAt")
    ;
    private String word;
    RoomColumn(String word) {
        this.word = word;
    }
}
