package com.lastone.core.util.validator.member;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class WorkoutDayValidator implements ConstraintValidator<WorkoutDay, String> {

    private static final List<String> VALID_DAYS = Arrays.asList("월", "화", "수", "목", "금", "토", "일");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }
        String[] days = value.split(",");
        for (String day : days) {
            if (!VALID_DAYS.contains(day.trim())) {
                return false;
            }
        }
        return true;
    }
}
