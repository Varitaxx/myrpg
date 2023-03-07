package de.varitaxx.myrpg.service;

import de.varitaxx.myrpg.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Data
@Service
@Transactional //Sorgt dafür, wenn die Transaktion abgelaufen ist, der Service neu aufgebaut wird
public class CleanUpService {

    private final TokenRepository tokenRepository;

    //@Scheduled(cron = "@hourly") // @yearly, @daily, @midnight, @hourly
    @Scheduled(cron = "0 0/30 * * * ?") // alle 30 Minuten
    public void deleteExpiredTokens() {
        tokenRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(7));
    }
}
