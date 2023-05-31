package com.group1.projectmanagementapi.card;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.card.models.Card;
import com.group1.projectmanagementapi.card.models.dto.CardRequest;
import com.group1.projectmanagementapi.card.models.dto.CardResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<CardResponse> createOne(@Valid @RequestBody CardRequest cardRequest) {
        Card newCard = cardRequest.convertToEntity();
        Card saveCard = this.cardService.createOne(newCard);
        CardResponse response = saveCard.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
