package de.varitaxx.myrpg.annotation;

import de.varitaxx.myrpg.validator.PasswordConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface Password {

    String message() default "..."; // TODO: Default-Message anpassen

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
