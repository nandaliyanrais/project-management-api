package com.group1.projectmanagementapi.task.models.dto.request;

import com.group1.projectmanagementapi.status.models.Status;
import com.group1.projectmanagementapi.task.models.Task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequest {

    private Long id;
    private String title;
    private String description;
    private Status status;
    
    public Task convertToEntity() {
        return Task.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .build();
    }

}
