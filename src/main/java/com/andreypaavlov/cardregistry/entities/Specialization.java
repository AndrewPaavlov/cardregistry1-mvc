package com.andreypaavlov.cardregistry.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Entity
@Table(name = "specializations")
@NoArgsConstructor
@Data
public class Specialization {
    @Id
    @Column(name = "specialization_name")
    private String specializationName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization_name")
    private Set<User> doctors;

}
