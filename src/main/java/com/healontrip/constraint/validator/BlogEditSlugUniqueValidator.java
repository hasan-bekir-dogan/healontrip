package com.healontrip.constraint.validator;

import com.healontrip.constraint.BlogEditSlugUniqueConstraint;
import com.healontrip.entity.BlogEntity;
import com.healontrip.service.BlogService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class BlogEditSlugUniqueValidator implements ConstraintValidator<BlogEditSlugUniqueConstraint, String> {
    @Autowired
    private BlogService blogService;

    @Override
    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        String[] parts = valueToValidate.split("-");
        String newSlug = "";

        Long blogId = Long.parseLong(parts[parts.length - 1]);

        for(int i = 0; i < parts.length - 1; i ++)
            newSlug += parts[i];

        BlogEntity requestedBlogEntity = blogService.findByEditSlugAndId(newSlug, blogId);

        if (requestedBlogEntity == null)
            return true;
        else
            return !requestedBlogEntity.getSlug().equals(newSlug);
    }
}