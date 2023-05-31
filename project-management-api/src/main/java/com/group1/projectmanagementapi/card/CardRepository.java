package com.group1.projectmanagementapi.card;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.card.models.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    
}
