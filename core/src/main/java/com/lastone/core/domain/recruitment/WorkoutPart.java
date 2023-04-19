package com.lastone.core.domain.recruitment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkoutPart {
    ALL("전신", "all.jpg"),
    CHEST("가슴", "chest.jpg"),
    BACK("등", "back.jpg"),
    SHOULDER("어깨", "shoulder.jpg"),
    LOWER("하체", "lower.jpg"),
    CORE("코어", "core.jpg");

    private final String text;

    private final String defaultImgUrl;

    WorkoutPart(String text, String defaultImgUrl) {
        this.text = text;
        this.defaultImgUrl = defaultImgUrl;
    }

    @JsonCreator
    public static WorkoutPart from(String text) {
        for (WorkoutPart part : WorkoutPart.values()) {
            if (part.getText().equals(text)) {
                return part;
            }
        }
        return null;
    }

    @JsonValue
    public String getText() {
        return text;
    }

    public String getDefaultImgUrl() {
        return defaultImgUrl;
    }
}
