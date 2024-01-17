package com.andreypaavlov.cardregistry.services;

import com.andreypaavlov.cardregistry.entities.Card;
import com.andreypaavlov.cardregistry.entities.CardMovement;
import com.andreypaavlov.cardregistry.repositories.CardMovementRepository;
import com.andreypaavlov.cardregistry.repositories.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardMovementService {

    private final CardMovementRepository cardMovementRepository;
    private final CardRepository cardRepository;

    public List<CardMovement> getCardMoments(String num) {
        Card card = cardRepository.getCardByNumber(num).orElseThrow(()->new EntityNotFoundException("Card with id " + num + " not found"));
        return cardMovementRepository.findAllByCardOrderByMovementTime(card);
    }

}
