package com.lastone.core.domain.applyment;

public enum ApplymentStatus {

    WAITING("대기중"),
    CANCLE("신청 취소"),
    FAILURE("매칭 실패"),
    SUCCESS("매칭 성공");

    private final String text;

    ApplymentStatus(String text) {
        this.text = text;
    }
}
