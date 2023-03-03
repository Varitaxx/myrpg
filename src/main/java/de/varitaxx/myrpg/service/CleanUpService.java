package de.varitaxx.myrpg.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional //Sorgt dafür, wenn die Transaktion abgelaufen ist, der Service neu aufgebaut wird
public class CleanUpService {
}
