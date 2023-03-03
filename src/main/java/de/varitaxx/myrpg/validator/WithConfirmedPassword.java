package de.varitaxx.myrpg.validator;

import de.varitaxx.myrpg.annotation.PasswordMatch;
@PasswordMatch
public interface WithConfirmedPassword {

    String getPassword();

    String getPasswordConfirmation();
}
