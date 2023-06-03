package com.group1.projectmanagementapi.task.models.dto.response;

import java.sql.Timestamp;

import com.group1.projectmanagementapi.status.models.Status;

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
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
