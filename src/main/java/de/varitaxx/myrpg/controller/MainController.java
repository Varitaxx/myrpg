package de.varitaxx.myrpg.controller;

import de.varitaxx.myrpg.exception.UserAlreadyExistsException;
import de.varitaxx.myrpg.exception.UserNotFoundException;
import de.varitaxx.myrpg.model.Charakter;
import de.varitaxx.myrpg.model.Token;
import de.varitaxx.myrpg.model.User;
import de.varitaxx.myrpg.model.UserDto;
import de.varitaxx.myrpg.repository.TokenRepository;
import de.varitaxx.myrpg.repository.UserRepository;
import de.varitaxx.myrpg.service.CustomEmailService;
import de.varitaxx.myrpg.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@Data
@RequiredArgsConstructor
public class MainController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final CustomEmailService emailService;

    private final UserService userService;


    @GetMapping("/")
    public String index(Model model) {

        return "home";
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
        //Token token = new Token(user, Token.TokenType.ACTIVATION);
        //tokenRepository.save(token);
        //emailService.sendSimpleEmail(user.getEmail(), "Registrierung", "Du hast dich erfolgreich registriert...");
        //emailService.sendHtmlEmail(user.getEmail(), "Registrierung");
        //emailService.sendHtmlRegisterEmail(user, token.getId());
        return "redirect:/register/success";
    }

    @GetMapping("register/success")
    public String registerSuccess(UserDto userDto, Model model) {
        model.addAttribute("success", true);
        return "register";
    }


    @GetMapping("admin")
    public String admin(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @RequestMapping(value = "admin/delete/{id}")
    String deleteUser(@PathVariable(name = "id")Long id, Model model) throws UserNotFoundException{

        userService.deleteUser(id);
        //return "admin";
        return admin(model);

    }

    @RequestMapping(value = "admin/edit/{id}")
    public ModelAndView showEditUserForm(@PathVariable(name = "id") Long id) throws UserNotFoundException{

        ModelAndView modelAndView = new ModelAndView("edituser");
        User user = userService.findUser(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute("user") User user, Model model) throws UserAlreadyExistsException, UserNotFoundException{
        userService.updateUser(user);
        return admin(model);
    }


}
