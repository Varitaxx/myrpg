package de.varitaxx.myrpg.validator;

import de.varitaxx.myrpg.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, WithConfirmedPassword> {

    public boolean isValid(WithConfirmedPassword obj, ConstraintValidatorContext context) {

        if(obj.getPassword() == null || !obj.getPassword().equals(obj.getPasswordConfirmation())) {
            return false;
        }

        return true;
    }
}
