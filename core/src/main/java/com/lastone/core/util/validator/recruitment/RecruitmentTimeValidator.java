package com.lastone.core.util.validator.recruitment;


import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class RecruitmentTimeValidator implements ConstraintValidator<RecruitmentTime, String> {

    private static final int HOUR = 0;
    private static final int MINUTE = 1;
    private static final int TIME_COUNT = 2;
    private static final String MINUTE_DEFAULT_VALUE = "00";
    private static final List<Integer> HOURS_TYPE = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        String[] times = value.split(":");
        if (times.length != TIME_COUNT) {
            return false;
        }
        String hour = times[HOUR];
        String minute = times[MINUTE];
        if (!HOURS_TYPE.contains(Integer.parseInt(hour))) {
            return false;
        }
        if (!minute.equals(MINUTE_DEFAULT_VALUE)) {
            return false;
        }
        return true;
    }
}
