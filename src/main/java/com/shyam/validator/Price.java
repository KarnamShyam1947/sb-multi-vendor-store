package com.shyam.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shyam.validator.impl.PriceValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = { PriceValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
    double min() default 0.0;

    String message() default "Price must be grater than zero";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
