package com.lastone.core.util.validator.recruitment;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class RecruitmentDateValidator implements ConstraintValidator<RecruitmentDate, String> {

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(date)) {
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate dateCondition;
        try {
            dateCondition = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return !now.isAfter(dateCondition);
    }
}
