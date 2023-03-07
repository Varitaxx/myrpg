package de.varitaxx.myrpg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data //Enthält u.a. Getter und Setter
@NoArgsConstructor //Parameterloser Konstruktor
@AllArgsConstructor //Konstruktor mit allen Parametern
@ToString
@Builder
@Entity
@EqualsAndHashCode
public class Item implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Place place;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Armortype armortype;

    private double price;

    private int amount;


    //Bindung zu anderen Tabellen
    @ManyToMany(
            cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Iventory> iventorys = new HashSet<>();


}
