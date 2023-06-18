package com.healontrip.constraint.validator;

import com.healontrip.constraint.FileNotNullConstraint;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileNotNullValidator implements ConstraintValidator<FileNotNullConstraint, MultipartFile> {
    @Override
    public boolean isValid(final MultipartFile valueToValidate, final ConstraintValidatorContext context) {
        return !valueToValidate.isEmpty();
    }
}