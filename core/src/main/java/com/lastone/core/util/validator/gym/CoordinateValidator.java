package com.lastone.core.util.validator.gym;

import com.nimbusds.oauth2.sdk.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (!StringUtils.isNumeric(value.replace(".", ""))) {
            return false;
        }
        return true;
    }
}
