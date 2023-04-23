package com.lastone.core.util.validator.member;

import com.lastone.core.domain.member.Day;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class WorkoutDayValidator implements ConstraintValidator<WorkoutDay, List<String>> {

    @Override
    public boolean isValid(List<String> days, ConstraintValidatorContext context) {
        if (days.isEmpty()) {
            return true;
        }
        return Day.isValidType(days) && Day.isDuplicatedDaysExist(days);
    }
}
