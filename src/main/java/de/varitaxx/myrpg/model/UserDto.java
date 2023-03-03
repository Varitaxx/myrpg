package de.varitaxx.myrpg.model;

import de.varitaxx.myrpg.annotation.PasswordMatch;
import de.varitaxx.myrpg.validator.WithConfirmedPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor //Parameterloser Konstruktor
@AllArgsConstructor //Konstruktor mit allen Parametern
@ToString
@PasswordMatch
public class UserDto implements WithConfirmedPassword {

    @NotEmpty
    private String username;

    @Email(regexp = ".*")
    @NotEmpty
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,25}$", message = "Passwort ist ungültig.")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,25}$", message = "Passwort ist ungültig.")
    private String passwordConfirmation;

    public User convert(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setEmail(email.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.CREATED);
        return user;
    }
}
