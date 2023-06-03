package com.group1.projectmanagementapi.task.models.dto.response;

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
public class TaskListResponse {

    private Long id;
    private String title;
    private Status status;
    
}
