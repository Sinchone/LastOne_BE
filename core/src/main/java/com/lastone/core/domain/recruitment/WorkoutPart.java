package com.lastone.core.domain.recruitment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum WorkoutPart {
    ALL("전신", "all-main.jpg", "all-list.jpg"),
    CHEST("가슴", "chest-main.jpg", "chest-list.jpg"),
    BACK("등", "back-main.jpg", "back-list.jpg"),
    SHOULDER("어깨", "shoulder-main.jpg", "shoulder-list.jpg"),
    LOWER("하체", "lower-main.jpg", "lower-list.jpg"),
    CORE("코어", "core-main.jpg", "core-list.jpg");

    private final String text;
    private final String mainDefaultImgUrl;
    private final String listDefaultImgUrl;

    WorkoutPart(String text, String mainDefaultImgUrl, String listDefaultImgUrl) {
        this.text = text;
        this.mainDefaultImgUrl = mainDefaultImgUrl;
        this.listDefaultImgUrl = listDefaultImgUrl;
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

    public String getListDefaultImgUrl() {
        return listDefaultImgUrl;
    }

    public String getMainDefaultImgUrl() {
        return mainDefaultImgUrl;
    }

    public static boolean isCorrectType(String text) {
        return Arrays.stream(WorkoutPart.values()).anyMatch(workoutPart -> workoutPart.getText().equals(text));
    }
}
