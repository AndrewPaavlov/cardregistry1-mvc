package com.andreypaavlov.cardregistry.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "card_movements")
@Data
public class CardMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "movement_time", nullable = false)
    private LocalDateTime movementTime;

    @Column(name = "next_room",nullable = false)
    private String nextRoom;


}