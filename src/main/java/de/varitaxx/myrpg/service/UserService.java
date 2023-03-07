package de.varitaxx.myrpg.service;

import de.varitaxx.myrpg.exception.UserAlreadyExistsException;
import de.varitaxx.myrpg.exception.UserNotFoundException;
import de.varitaxx.myrpg.model.User;
import de.varitaxx.myrpg.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Data //Enthält u.a. Getter und Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;



    public User findUser(Long id) throws UserNotFoundException{
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public List<User> findUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional(rollbackOn = UserAlreadyExistsException.class)
    public void addUser(User user) throws UserAlreadyExistsException{
        if(userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        userRepository.save(user);
    }

    @Transactional(rollbackOn = UserNotFoundException.class)
    public User updateUser(User user)throws UserNotFoundException{
        userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    public boolean isValid(User user){
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        return constraintViolations.isEmpty();
    }

    public void deleteUser(Long id) throws UserNotFoundException{
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }




}
