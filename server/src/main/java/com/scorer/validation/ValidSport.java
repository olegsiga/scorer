package com.scorer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSportValidator.class)
public @interface ValidSport {
    String message() default "invalid-sport";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
