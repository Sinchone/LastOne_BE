package com.lastone.core.domain.recruitment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PreferGender {

    BOTH("무관"),

    MALE("남성"),

    FEMALE("여성");

    private final String text;

    PreferGender(String text) {
        this.text = text;
    }

    @JsonCreator
    public static PreferGender from(String text) {
        for (PreferGender gender : PreferGender.values()) {
            if (gender.getText().equals(text)) {
                return gender;
            }
        }
        return null;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}