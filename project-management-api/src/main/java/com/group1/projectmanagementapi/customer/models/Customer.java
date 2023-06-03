package com.group1.projectmanagementapi.customer.models;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerCreateResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerUpdateResponse;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectListResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private ApplicationUser applicationUser;

    private String imageUrl;

    // @OneToMany(mappedBy = "customer")
    // private List<Project> projects;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "customer_project", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public CustomerResponse convertToResponse() {
        List<ProjectListResponse> projectLists = this.projects.stream()
                .sorted(Comparator.comparing(Project::getUpdatedAt).reversed())
                .map(Project::convertToListResponse)
                .toList();
        return CustomerResponse.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .projects(projectLists)
                .createdAt(this.createdAt)
                .build();
    }

    public CustomerCreateResponse convertToCreateResponse() {
        return CustomerCreateResponse.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .createdAt(this.createdAt)
                .build();
    }

    public CustomerUpdateResponse convertToUpdateResponse() {
        return CustomerUpdateResponse.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .updatedAt(this.updatedAt)
                .build();
    }

    // @ManyToMany
    // @JoinTable(name = "customer_project", joinColumns = @JoinColumn(name =
    // "customer_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    // private List<Project> projectLists;

}
