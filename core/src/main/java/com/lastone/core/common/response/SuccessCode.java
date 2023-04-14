package com.lastone.core.common.response;

import lombok.Getter;

@Getter
public enum SuccessCode {
    CREATED_CHAT_ROOM(201, "채팅방이 생성되었습니다."),
    DELETED_CHAT_ROOM(200, "채팅방이 삭제되었습니다."),
    GET_CHAT_ROOM_LIST(200, "채팅방 목록 조회에 성공하였습니다.");

    private int status;
    private String message;

    SuccessCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
