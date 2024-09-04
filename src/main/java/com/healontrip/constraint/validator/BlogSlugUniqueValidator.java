package com.healontrip.constraint.validator;

import com.healontrip.constraint.BlogSlugUniqueConstraint;
import com.healontrip.entity.BlogEntity;
import com.healontrip.service.BlogService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class BlogSlugUniqueValidator implements ConstraintValidator<BlogSlugUniqueConstraint, String> {
    @Autowired
    private BlogService blogService;

    @Override
    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        BlogEntity requestedBlogEntity = blogService.findBySlug(valueToValidate);

        if (requestedBlogEntity == null)
            return true;
        else
            return !requestedBlogEntity.getSlug().equals(valueToValidate);
    }
}