package de.varitaxx.myrpg.controller;

import de.varitaxx.myrpg.model.Token;
import de.varitaxx.myrpg.model.User;
import de.varitaxx.myrpg.model.UserDto;
import de.varitaxx.myrpg.repository.TokenRepository;
import de.varitaxx.myrpg.repository.UserRepository;
import de.varitaxx.myrpg.service.CustomEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Data
@RequiredArgsConstructor
public class MainController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final CustomEmailService emailService;


    @GetMapping("/")
    public String index(Model model) {

        return "home";
    }

    @GetMapping("chars")
    public String chars(Model model){
        return "chars";
    }

    @GetMapping({"login", "login/{sub}"})
    public String login(@PathVariable Optional<String> sub, Model model) {
        sub.ifPresent(s -> {
            model.addAttribute(s, true);
        });
        return "login";
    }

    @GetMapping("register")
    public String register(UserDto userDto, Model model) {
        return "register";
    }

    @PostMapping("register")
    public String registerProcess(@Valid UserDto userDto, BindingResult result, Model model) throws MessagingException, MessagingException {

        if(!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.userDto", "Passwörter müssen übereinstimmen.");
            // Ein Fehler für das Objekt im userDto und das Feld passwordConfirmation
        }

        if(result.hasErrors()) {
            return "register";
        }
        User user = userDto.convert(passwordEncoder);
        userRepository.save(user);
        Token token = new Token(user, Token.TokenType.ACTIVATION);
        tokenRepository.save(token);
        //emailService.sendSimpleEmail(user.getEmail(), "Registrierung", "Du hast dich erfolgreich registriert...");
        //emailService.sendHtmlEmail(user.getEmail(), "Registrierung");
        emailService.sendHtmlRegisterEmail(user, token.getId());
        return "redirect:/register/success";
    }

    @GetMapping("register/success")
    public String registerSuccess(UserDto userDto, Model model) {
        model.addAttribute("success", true);
        return "register";
    }



}
