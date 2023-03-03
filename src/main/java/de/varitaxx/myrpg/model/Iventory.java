package de.varitaxx.myrpg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data //Enthält u.a. Getter und Setter
@NoArgsConstructor //Parameterloser Konstruktor
@AllArgsConstructor //Konstruktor mit allen Parametern
@ToString
@Builder
@Entity
@EqualsAndHashCode
public class Iventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String helmet;

    private String armor;

    private String trousers;

    private String shoes;

    private String mainhand;

    private String secondhand;


    //Beziehungen zu anderen Tabellen
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Charakter charakter;

    @ManyToMany(mappedBy="iventorys",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<Item> items = new HashSet<>();
}
