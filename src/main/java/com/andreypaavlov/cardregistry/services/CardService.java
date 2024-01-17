package com.andreypaavlov.cardregistry.services;

import com.andreypaavlov.cardregistry.AuxiliaryUtils;
import com.andreypaavlov.cardregistry.entities.BloodType;
import com.andreypaavlov.cardregistry.entities.Card;
import com.andreypaavlov.cardregistry.entities.CardMovement;
import com.andreypaavlov.cardregistry.entities.User;
import com.andreypaavlov.cardregistry.repositories.CardMovementRepository;
import com.andreypaavlov.cardregistry.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMovementRepository cardMovementRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CardService.class);

    @CachePut(value = "cards", key = "#card.id")
    public Card saveCard(Card card) {
        LOG.info("Calling card save method...");
        Card savedCard = cardRepository.save(card);
        return savedCard;
    }

    @Cacheable(value = "cards", key = "#id")
    public Card getCardById(Long id) {
        return cardRepository.findById(id).get();
    }

    @Cacheable(value = "cards", key = "#number", unless = "#result==null")
    public Optional<Card> getCardByNumber(String number) {
        LOG.info("Calling find card by number method...");
        Optional<Card> optional = cardRepository.getCardByNumber(number);
        return optional;
    }


    public List<Card> getCardsByFullName(String l, String f, String p) {
        LOG.info("Calling method find by full name");
        List<Card> cards = cardRepository.findByFullName(l, f, p);
        return cards;
    }

    @CacheEvict(value = "cards", key = "#updated.number")
    public Card updateCard(String num, Card updated, String description, String date, String bloodType, String birthDate, User doctor,String next) {
        Card existed = cardRepository.getCardByNumber(num).orElseThrow(() -> new RuntimeException("Card not found"));
        LinkedHashMap<String, String> oldHistory = existed.getHistory();
        System.out.println("ex " + existed);
        System.out.println(updated);

        Arrays.stream(existed.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(updated);
                field.set(existed, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        oldHistory.put(description,date);
        existed.setHistory(oldHistory);

        existed.setDate(LocalDate.parse(birthDate, AuxiliaryUtils.dateFormatter));
        existed.setBloodType(BloodType.findByAnnotation(bloodType));

        CardMovement cardMovement = new CardMovement();
                cardMovement.setDoctor(doctor);
                cardMovement.setCard(updated);
                cardMovement.setMovementTime(LocalDateTime.now());
                cardMovement.setNextRoom(next);
        cardMovementRepository.save(cardMovement);
        return cardRepository.save(existed);
    }
}
