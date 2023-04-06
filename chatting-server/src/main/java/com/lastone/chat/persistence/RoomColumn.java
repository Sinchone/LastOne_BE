package com.lastone.chat.persistence;


import lombok.Getter;

@Getter
public enum RoomColumn {
    ID("id"),
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
