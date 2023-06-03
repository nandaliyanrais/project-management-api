package com.group1.projectmanagementapi.project.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerCreateResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectListResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectResponse;
import com.group1.projectmanagementapi.task.models.Task;
import com.group1.projectmanagementapi.task.models.dto.response.TaskResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

        @Column(nullable = false)
        private String title;

        // @ManyToOne
        // @JoinColumn(name = "customer_id")
        // @Cascade(CascadeType.ALL)
        // @JsonIgnore
        // private Customer customers;

        @OneToMany(mappedBy = "project")
        @Builder.Default
        // @Cascade(CascadeType.ALL)
        private List<Task> tasks = new ArrayList<>();

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        private Timestamp createdAt;

        @UpdateTimestamp
        private Timestamp updatedAt;

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE
        }, mappedBy = "projects")

        @Builder.Default
        private List<Customer> projectMembers = new ArrayList<>();

        public ProjectResponse convertToResponse() {
                // List<TaskListResponse> taskLists = this.tasks.stream()
                // .sorted(Comparator.comparing(Task::getUpdatedAt).reversed())
                // .map(Task::convertToListResponse)
                // .toList();

                List<CustomerCreateResponse> customers = this.projectMembers.stream()
                                .map(c -> c.convertToCreateResponse())
                                .toList();

                // List<TaskResponse> tasks = this.tasks.stream()
                // .filter(task -> task.getStatus().equals("IN_DEV"))
                // .map(Task::convertToResponse)
                // .collect(Collectors.toList());

                List<TaskResponse> tasks = this.tasks.stream().map(task -> task.convertToResponse()).toList();

                return ProjectResponse.builder()
                                .id(this.id)
                                .title(this.title)
                                .tasks(tasks)
                                .projectMembers(customers)
                                .createdAt(this.createdAt)
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
