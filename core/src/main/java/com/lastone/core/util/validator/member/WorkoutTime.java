package com.lastone.core.util.validator.member;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WorkoutTimeValidator.class)
public @interface WorkoutTime {
    String message() default "유효 하지 않은 시간입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
