package com.lastone.core.util.validator.recruitment;

import com.lastone.core.domain.recruitment.WorkoutPart;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WorkoutPartValidator implements ConstraintValidator<ValidateWorkoutPart, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true;
        }
        return WorkoutPart.isCorrectType(value);
    }
}
