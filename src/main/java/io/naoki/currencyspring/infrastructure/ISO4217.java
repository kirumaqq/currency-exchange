package io.naoki.currencyspring.infrastructure;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Size(min = 3, max = 3)
public @interface ISO4217 {

    String message() default "Invalid currency code. Follow ISO 4217 format(code length must be equal to 3)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
