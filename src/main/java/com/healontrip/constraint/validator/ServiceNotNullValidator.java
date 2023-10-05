package com.healontrip.constraint.validator;

import com.healontrip.constraint.ServiceNotNullConstraint;
import com.healontrip.dto.Role;
import com.healontrip.service.AuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class ServiceNotNullValidator implements ConstraintValidator<ServiceNotNullConstraint, String> {
    @Autowired
    private AuthService authService;

    @Override
    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        String role = authService.getRole();

        if(role.equals(Role.PATIENT.toString()))
            return true;

        return !valueToValidate.isEmpty();
    }
}