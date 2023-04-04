package com.lastone.core.util.validator.member;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WorkoutTimeValidator implements ConstraintValidator<WorkoutTime, String> {

    private static final int HOUR = 0;

    private static final int MINUTE = 1;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String[] times = value.split(":");
        if (times.length != 2) {
            return false;
        }
        int hour = Integer.parseInt(times[HOUR]);
        int minute = Integer.parseInt(times[MINUTE]);
        if (hour < 0 || hour > 23) {
            return false;
        }
        if (minute < 0 || minute > 59) {
            return false;
        }
        return true;
    }
}
