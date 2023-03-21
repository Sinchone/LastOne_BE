package com.lastone.core.domain.chat;

public enum ChatStatus {
    NORMAL("정상"),
    DELETED("삭제됨"),
    BLOCKED("차단됨")
    ;

    private String korWord;

    ChatStatus(String korWord) {
        this.korWord = korWord;
    }
}
