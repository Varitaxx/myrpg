package de.varitaxx.myrpg.service;

import de.varitaxx.myrpg.model.User;
import de.varitaxx.myrpg.model.UserStatus;
import de.varitaxx.myrpg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsernameIgnoreCase(username);

        if(opt.isPresent()) {
            User u = opt.get();
            return u;
            /*return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRole().toString())
                    .disabled(!u.getStatus().equals(UserStatus.ACTIVE))
                    .build();*/
        };

        throw new RuntimeException("User nicht gefunden");
    }
}
