package com.group1.projectmanagementapi.project.models.dto.response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.group1.projectmanagementapi.customer.models.dto.response.CustomerCreateResponse;
import com.group1.projectmanagementapi.task.models.dto.response.TaskResponse;

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
    private String title;
    private List<TaskResponse> tasks;
    private List<CustomerCreateResponse> projectMembers;
    @Builder.Default
    private List<String> statuses = new ArrayList<>();
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
