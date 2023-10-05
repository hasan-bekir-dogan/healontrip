package com.healontrip.constraint.validator;

import com.healontrip.constraint.BooleanNotNullConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BooleanNotNullValidator implements ConstraintValidator<BooleanNotNullConstraint, Boolean> {
    @Override
    public boolean isValid(final Boolean valueToValidate, final ConstraintValidatorContext context) {
        return valueToValidate;
    }
}