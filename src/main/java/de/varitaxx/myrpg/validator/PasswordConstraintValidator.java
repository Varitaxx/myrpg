package de.varitaxx.myrpg.validator;

import de.varitaxx.myrpg.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(
                new LengthRule(8,32),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );

        RuleResult result = validator.validate(new PasswordData(password));

        if(result.isValid()) {
            return true;
        }

        String tpl = validator.getMessages(result).stream().collect(Collectors.joining(" "));
        context.buildConstraintViolationWithTemplate(tpl)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
