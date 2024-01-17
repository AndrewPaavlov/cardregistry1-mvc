package com.andreypaavlov.cardregistry.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;
    private String firstname;
    private String patronymic;

    @Column(name = "birth_date")
    private LocalDate date;

    private String address;
    private Integer height;
    private Double weight;
    @Column(unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType = BloodType.U_U;

    @JdbcTypeCode(SqlTypes.JSON)
    private LinkedHashMap<String,String> history=new LinkedHashMap<>();

//    @OneToMany(mappedBy = "card")
//    private Set<CardMovement> cardMovements = new LinkedHashSet<>();

}
