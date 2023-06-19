package com.lastone.core.util.validator.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PreferGenderValidator implements ConstraintValidator<ValidatePreferGender, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true;
        }
        return PreferGender.isCorrectType(value);
    }
}
