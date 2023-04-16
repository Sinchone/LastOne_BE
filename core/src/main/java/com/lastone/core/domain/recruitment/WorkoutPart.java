package com.lastone.core.domain.recruitment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkoutPart {
    ALL("전신"),
    CHEST("가슴"),
    BACK("등"),
    SHOULDER("어깨"),
    LOWER("하체"),
    CORE("코어");

    private final String text;

    WorkoutPart(String text) {
        this.text = text;
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
}
