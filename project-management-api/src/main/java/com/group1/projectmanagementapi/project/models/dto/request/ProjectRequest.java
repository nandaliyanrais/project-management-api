package com.group1.projectmanagementapi.project.models.dto.request;

import com.group1.projectmanagementapi.project.models.Project;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;

    private String projectMember;
    
    public Project convertToEntity() {
        return Project.builder()
                .id(this.id)
                .title(this.title)
                .build();
    }
}
