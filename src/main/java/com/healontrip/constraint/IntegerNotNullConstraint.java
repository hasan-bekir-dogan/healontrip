package com.healontrip.constraint;

import com.healontrip.constraint.validator.IntegerNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = IntegerNotNullValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface IntegerNotNullConstraint {
    String message() default "Field can not be empty";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
