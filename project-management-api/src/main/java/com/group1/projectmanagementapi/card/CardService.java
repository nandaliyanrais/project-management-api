package com.group1.projectmanagementapi.card;

import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.card.exception.CardNotFoundException;
import com.group1.projectmanagementapi.card.models.Card;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card findOneById(Long id) {
        return this.cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException());
    }

    public Card createOne(Card card) {
        return this.cardRepository.save(card);
    }
    
}
