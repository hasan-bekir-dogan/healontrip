package com.healontrip.constraint;

import com.healontrip.constraint.validator.PhoneNumberNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PhoneNumberNotNullValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface PhoneNumberNotNullConstraint {
    String message() default "Field can not be empty";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
