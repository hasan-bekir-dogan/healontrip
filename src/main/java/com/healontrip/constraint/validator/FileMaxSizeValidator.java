package com.healontrip.constraint.validator;

import com.healontrip.constraint.FileMaxSizeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileMaxSizeValidator implements ConstraintValidator<FileMaxSizeConstraint, MultipartFile[]> {
    private static final long FILE_SIZE = 1048576L;

    @Override
    public boolean isValid(final MultipartFile[] valueToValidate, final ConstraintValidatorContext context) {
        double size = 0;

        for (int i = 0; i < valueToValidate.length; i++) {
            size += valueToValidate[i].getSize();
        }

        return !(size >= FILE_SIZE);
    }
}
