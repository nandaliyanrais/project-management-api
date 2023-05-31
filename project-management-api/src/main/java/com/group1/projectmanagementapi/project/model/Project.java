package com.group1.projectmanagementapi.project.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.group1.projectmanagementapi.card.Card;
import com.group1.projectmanagementapi.customer.Customer;
import com.group1.projectmanagementapi.project.model.dto.ProjectResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "project")
    private List<Card> cards;

    @ManyToMany(mappedBy = "projectLists")
    private List<Customer> projectMembers;

    public ProjectResponse convertToResponse() {
        return ProjectResponse.builder()
        .id(this.id)
        .name(this.name)
        .cards(this.cards)
        .projectMembers(this.projectMembers)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .build();
    }
}
