package de.varitaxx.myrpg;

import de.varitaxx.myrpg.model.*;
import de.varitaxx.myrpg.repository.CharRepository;
import de.varitaxx.myrpg.repository.ItemRepository;
import de.varitaxx.myrpg.repository.IventoryRepository;
import de.varitaxx.myrpg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor // Baut einen Konstruktor für finale Felder
@Transactional
public class MyrpgApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    private final CharRepository charRepository;

    private final ItemRepository itemRepository;

    private final IventoryRepository iventoryRepository;

    private final PasswordEncoder passwordEncoder;



    public static void main(String[] args) {
        SpringApplication.run(MyrpgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        /*var user = User.builder()
                .username("Horst")
                .email("horst@varitaxx.de")
                .password(passwordEncoder.encode("geheim#123"))
                .role(UserRole.USER)
                .status(UserStatus.BLOCKED)
                .build();
        userRepository.save(user);


         */



        /*
        var user = User.builder()
                .username("Soulbox")
                .email("soulbox@soulbox.eu")
                .password(passwordEncoder.encode("geheim#123"))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);
    */


       /* var charakter = Charakter.builder()
                .name("Kelda Khorram-Din")
                .gender(Gender.FEMALE)
                .age(24)
                .klasse(Klasse.WARRIOR)
                .race(Race.KHAANAN)
                .rank(Rank.NOVICE)
                .user(user)
                .build();
        charRepository.save(charakter);


        var iventory = Iventory.builder()
                .helmet("Eisenhelm")
                .armor("Eisenrüstung")
                .trousers("Eisenhose")
                .shoes("Eisenstiefel")
                .mainhand("Einhandaxt")
                .secondhand("Einhandaxt")
                .charakter(charakter)
                .build();
         iventoryRepository.save(iventory);

         */



    }
}
