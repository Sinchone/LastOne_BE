package com.lastone.core.domain.member;

import java.util.*;
import java.util.stream.Collectors;

public enum Day {
    MONDAY("월", 0),
    TUESDAY("화", 1),
    WEDNESDAY("수", 2),
    THURSDAY("목", 3),
    FRIDAY("금", 4),
    SATURDAY("토", 5),
    SUNDAY("일", 6);
    private final String text;
    private final int order;
    Day(String text, int order) {
        this.text = text;
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public static Day from(String text) {
        for (Day day : Day.values()) {
            if (day.getText().equals(text)) {
                return day;
            }
        }
        return null;
    }

    public static boolean isValidType(List<String> inputs) {
        List<String> dayTexts = Arrays.stream(Day.values()).map(day -> day.text).collect(Collectors.toList());
        for (String input : inputs) {
            if (!dayTexts.contains(input.trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDuplicatedDaysExist(List<String> inputs) {
        HashSet<String> days = new HashSet<>();
        for (String input : inputs) {
            if (days.contains(input.trim())) {
                return false;
            }
            days.add(input.trim());
        }
        return true;
    }

    public static String sortListToString(List<String> inputs) {
        List<Day> days = new ArrayList<>();
        for (String input : inputs) {
            days.add(Day.from(input));
        }
        days.sort((Comparator.comparingInt(o -> o.order)));
        StringBuffer sb = new StringBuffer();
        for (Day day : days) {
            sb.append(day.getText()).append(" ");
        }
        return sb.toString();
    }
}
