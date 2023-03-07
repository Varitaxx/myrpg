package de.varitaxx.myrpg.repository;

import de.varitaxx.myrpg.model.Iventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IventoryRepository extends JpaRepository<Iventory, Long> {
}
