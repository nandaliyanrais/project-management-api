package com.group1.projectmanagementapi.card.models.dto;

import com.group1.projectmanagementapi.card.models.Card;
import com.group1.projectmanagementapi.card.models.Status;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    
    @NotEmpty(message = "Title is required")
    private String title;

    private String description;

    private Status status;

    public Card convertToEntity() {
        return Card.builder()
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .build();
    }

}
