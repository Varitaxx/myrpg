package de.varitaxx.myrpg.service;

import de.varitaxx.myrpg.exception.CharakterNameAlreadyExistsException;
import de.varitaxx.myrpg.exception.CharakterNotFoundException;
import de.varitaxx.myrpg.model.Charakter;
import de.varitaxx.myrpg.repository.CharRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Data //Enthält u.a. Getter und Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class CharService {

    @Autowired
    private CharRepository charRepository;

    @Autowired
    private Validator validator;

    public Charakter findChar(Long id) throws CharakterNotFoundException {
        Optional<Charakter> charakter = charRepository.findById(id);
        return charakter.orElseThrow(CharakterNotFoundException::new);
    }

    public List<Charakter>findChars(){
        List<Charakter> charakters = charRepository.findAll();
        return charakters;
    }

    /*
    public List<Charakter>findCharsByUserId(){
        List<Charakter> charakters = charRepository.findCharsByUserId(id);
        return charakters;
    }

     */

    @Transactional(rollbackOn = CharakterNameAlreadyExistsException.class)
    public void addChara(Charakter charakter)throws CharakterNameAlreadyExistsException{
        if (charRepository.findByNameIgnoreCase(charakter.getName()).isPresent()){
            throw new CharakterNameAlreadyExistsException();
        }
        charRepository.save(charakter);
    }

    private boolean isValid(Charakter charakter) {
        Set<ConstraintViolation<Charakter>> constraintViolations = validator.validate(charakter);
        return constraintViolations.isEmpty();
    }

    public void deleteChar(Long id) throws CharakterNotFoundException{
        Charakter chara = charRepository.findById(id).orElseThrow(CharakterNotFoundException::new);
        charRepository.delete(chara);
    }

    @Transactional(rollbackOn = CharakterNotFoundException.class)
    public Charakter updateChar(Charakter charakter) throws CharakterNotFoundException {
        charRepository.findById(charakter.getId()).orElseThrow(CharakterNotFoundException::new);
        return charRepository.save(charakter);
    }






}
