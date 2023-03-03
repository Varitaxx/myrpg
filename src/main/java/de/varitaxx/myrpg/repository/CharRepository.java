package de.varitaxx.myrpg.repository;

import de.varitaxx.myrpg.model.Charakter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CharRepository extends JpaRepository<Charakter, UUID> {


}
