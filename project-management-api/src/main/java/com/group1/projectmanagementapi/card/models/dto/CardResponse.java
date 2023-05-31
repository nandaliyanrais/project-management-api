package com.group1.projectmanagementapi.card.models.dto;

import com.group1.projectmanagementapi.card.models.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private Long id;
    private String title;
    private String description;
    private Status status;
    
}
