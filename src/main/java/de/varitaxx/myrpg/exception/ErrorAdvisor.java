package de.varitaxx.myrpg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, String> handleUserAlreadyExistsException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "User existiert bereits");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CharakterNameAlreadyExistsException.class)
    public Map<String, String> handleCharakterNameExistsException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Charname existiert bereits");
        return errors;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "User nicht gefunden");
        return errors;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CharakterNotFoundException.class)
    public Map<String, String> handleCharakterNotFoundException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Charakter nicht gefunden");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errors.put(field, msg);
        });

        return errors;
    }



}
