package com.group1.projectmanagementapi.status.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.projectmanagementapi.status.models.dto.response.StatusResponse;
import com.group1.projectmanagementapi.task.models.Task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Long id;

    private String status;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Task> task;

    public Status(String status) {
        this.status = status;
    }

    public Status(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public StatusResponse convertToResponse() {
        return StatusResponse.builder().id(this.id).status(this.status).build();
    }
}
