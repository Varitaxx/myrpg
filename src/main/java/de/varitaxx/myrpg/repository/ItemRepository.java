package de.varitaxx.myrpg.repository;

import de.varitaxx.myrpg.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
