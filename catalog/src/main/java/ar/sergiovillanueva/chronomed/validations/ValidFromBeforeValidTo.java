package ar.sergiovillanueva.chronomed.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFromBeforeValidToValidator.class)
public @interface ValidFromBeforeValidTo {
    String message() default "validFrom cannot be after validTo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
