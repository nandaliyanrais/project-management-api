package com.group1.projectmanagementapi.project.model.dto;

import java.sql.Timestamp;
import java.util.List;

import com.group1.projectmanagementapi.card.Card;
import com.group1.projectmanagementapi.customer.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    
    private Long id;
    private String name;
    private List<Card> cards;
    private List<Customer> projectMembers;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
