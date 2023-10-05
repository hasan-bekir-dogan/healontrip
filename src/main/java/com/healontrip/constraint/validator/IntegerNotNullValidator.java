package com.healontrip.constraint.validator;

import com.healontrip.constraint.IntegerNotNullConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerNotNullValidator implements ConstraintValidator<IntegerNotNullConstraint, Integer> {
    @Override
    public boolean isValid(final Integer valueToValidate, final ConstraintValidatorContext context) {
        return valueToValidate != 0;
    }
}