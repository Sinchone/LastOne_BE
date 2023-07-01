package com.lastone.core.domain.recruitment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum PreferGender {

    BOTH("성별무관"),

    MALE("남성만"),

    FEMALE("여성만");

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

    public static boolean isCorrectType(String text) {
        return Arrays.stream(PreferGender.values()).anyMatch(preferGender -> preferGender.getText().equals(text));
    }
}
