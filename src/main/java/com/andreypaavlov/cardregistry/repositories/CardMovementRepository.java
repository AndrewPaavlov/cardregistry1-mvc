package com.andreypaavlov.cardregistry.repositories;

import com.andreypaavlov.cardregistry.entities.Card;
import com.andreypaavlov.cardregistry.entities.CardMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardMovementRepository extends JpaRepository<CardMovement, Long> {

    List<CardMovement> findAllByCardOrderByMovementTime(Card card);
}
