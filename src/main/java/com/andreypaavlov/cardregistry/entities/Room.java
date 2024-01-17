package com.andreypaavlov.cardregistry.entities;

import jakarta.persistence.*;
import lombok.Data;

import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "type",nullable = false)
    private String type;

}