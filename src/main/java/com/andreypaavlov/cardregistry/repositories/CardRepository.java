package com.andreypaavlov.cardregistry.repositories;

import com.andreypaavlov.cardregistry.entities.Card;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Transactional
    @Query("FROM Card c WHERE c.number = :number")
    Optional<Card> getCardByNumber(@Param("number") String number);
    @Query("SELECT c FROM Card c WHERE LOWER(c.lastname) = LOWER(:lastName) AND LOWER(c.firstname) = LOWER(:firstName) AND LOWER(c.patronymic) = LOWER(:patronymic) ORDER BY c.lastname, c.firstname, c.patronymic")
    List<Card> findByFullName(@Param("lastName") String lastName, @Param("firstName") String firstName, @Param("patronymic") String patronymic);
}

