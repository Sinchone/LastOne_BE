package com.lastone.core.domain.recruitment;

public enum RecruitmentStatus {
    RECRUITING("모집중"),
    COMPLETE("모집완료"),
    EXPIRATION("기간만료");

    private final String text;

    RecruitmentStatus(String text) {
        this.text = text;
    }
}
