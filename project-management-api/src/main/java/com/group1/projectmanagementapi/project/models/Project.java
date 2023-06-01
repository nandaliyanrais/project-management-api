package com.group1.projectmanagementapi.project.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectCreateResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectListResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectUpdateResponse;
import com.group1.projectmanagementapi.task.models.Task;
import com.group1.projectmanagementapi.task.models.dto.response.TaskListResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "projects")
    // @Cascade(CascadeType.ALL)
    private List<Task> tasks;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // @ManyToMany(mappedBy = "projectLists")
    // private List<Customer> projectMembers;

    public ProjectResponse convertToResponse() {
        List<TaskListResponse> taskLists = this.tasks.stream()
                                            .sorted(Comparator.comparing(Task::getUpdatedAt).reversed())
                                            .map(Task::convertToListResponse)
                                            .toList();
        return ProjectResponse.builder()
                .id(this.id)
                .title(this.title)
                .tasks(taskLists)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public ProjectCreateResponse convertToCreateResponse() {
        return ProjectCreateResponse.builder()
                .id(this.id)
                .title(this.title)
                .createdAt(this.createdAt)
                .build();
    }

    public ProjectUpdateResponse convertToUpdateResponse() {
        return ProjectUpdateResponse.builder()
                .id(this.id)
                .title(this.title)
                .updatedAt(this.updatedAt)
                .build();
    }

    public ProjectListResponse convertToListResponse() {
        return ProjectListResponse.builder()
                .id(this.id)
                .title(this.title)
                .build();
    }
}
