package io.naoki.currencyspring.infrastructure;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Size(min = 3, max = 3, message = "Invalid currency code. Follow ISO 4217 format. Code length must be equal to 3")
@Pattern(regexp = "\\A\\pL+\\z", message = "Invalid currency code. Follow ISO 4217 format. Code must contain only alphabetic characters")
public @interface ISO4217 {

    String message() default "Invalid currency code. Follow ISO 4217 format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
