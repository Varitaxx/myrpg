package de.varitaxx.myrpg.service;


import de.varitaxx.myrpg.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class CustomEmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;// bei mir Thymeleaf


    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("rpg@localhost");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }

    public void sendHtmlEmail(String to, String subject) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("rpg@localhost");
        helper.setTo(to);
        helper.setSubject(subject);
        Map<String, Object> vars = new HashMap<>();
        vars.put("link", "http://localhost:8080/activate/1234");
        helper.setText(renderTemplateEmail(vars, "email-register"), true);
        mailSender.send(msg);
    }

    public void sendHtmlRegisterEmail(User user, UUID token) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("rpg@localhost");
        helper.setTo(user.getEmail());
        helper.setSubject("Registrierung");
        Map<String, Object> vars = new HashMap<>();
        vars.put("link", "http://localhost:8080/activate?token=" + token);
        helper.setText(renderTemplateEmail(vars, "email-register"), true);
        mailSender.send(msg);
    }

    public void sendHtmlForgotEmail(User user, UUID token) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("rpg@localhost");
        helper.setTo(user.getEmail());
        helper.setSubject("Passwort zurücksetzen");
        Map<String, Object> vars = new HashMap<>();
        vars.put("link", "http://localhost:8080/forgot/reset?token=" + token);
        helper.setText(renderTemplateEmail(vars, "email-forgot"), true);
        mailSender.send(msg);
    }

    public String renderTemplateEmail(Map<String, Object> vars, String tpl) {
        Context context = new Context();
        context.setVariables(vars);
        String html = templateEngine.process(tpl, context);
        return html;
    }
}
