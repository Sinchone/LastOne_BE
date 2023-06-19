package com.lastone.core.domain.application;

public enum ApplicationStatus {

    WAITING("대기중"),
    CANCEL("신청 취소"),
    FAILURE("매칭 실패"),
    SUCCESS("매칭 성공");

    private final String text;

    ApplicationStatus(String text) {
        this.text = text;
    }
}
