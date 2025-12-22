package com.scorer.validation;

import com.scorer.entity.Sport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidSportValidator implements ConstraintValidator<ValidSport, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return Sport.fromDisplayName(value) != null;
    }
}
