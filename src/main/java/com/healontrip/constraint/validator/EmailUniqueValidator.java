package com.healontrip.constraint.validator;

import com.healontrip.constraint.EmailUniqueConstraint;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.AuthService;
import com.healontrip.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class EmailUniqueValidator implements ConstraintValidator<EmailUniqueConstraint, String> {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Override
    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        UserEntity requestedUserEntity = userService.findByEmail(valueToValidate);
        UserEntity currentUserEntity = userService.findById(authService.getUserId());

        if (requestedUserEntity == null)
            return true;
        else return currentUserEntity.getEmail().equals(requestedUserEntity.getEmail());
    }
}