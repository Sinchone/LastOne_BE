package com.lastone.core.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Meridiem {
    AM("오전"),
    PM("오후");

    private final String text;

    Meridiem(String text) {
        this.text = text;
    }

    @JsonCreator
    public static Meridiem from(String text) {
        for (Meridiem meridiem : Meridiem.values()) {
            if (meridiem.getText().equals(text)) {
                return meridiem;
            }
        }
        return null;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}
