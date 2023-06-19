package com.lastone.core.util.validator.recruitment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WorkoutPartValidator.class)
public @interface ValidateWorkoutPart {
    String message() default "유효하지 않은 운동 부위입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
