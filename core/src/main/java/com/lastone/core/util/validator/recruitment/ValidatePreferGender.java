package com.lastone.core.util.validator.recruitment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PreferGenderValidator.class)
public @interface ValidatePreferGender {
    String message() default "유효하지 않은 성별입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
