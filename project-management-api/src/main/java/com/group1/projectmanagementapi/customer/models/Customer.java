package com.group1.projectmanagementapi.customer.models;

import java.sql.Timestamp;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerRegisterResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerResponse;
import com.group1.projectmanagementapi.image.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    
    @OneToOne
    @Cascade(CascadeType.ALL)
    private ApplicationUser applicationUser;

    // @OneToOne
    // @Cascade(CascadeType.ALL)
    // private Image imageurl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public CustomerResponse convertToResponse() {
        return CustomerResponse.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .createdAt(this.createdAt)
                .build();
    }

}
