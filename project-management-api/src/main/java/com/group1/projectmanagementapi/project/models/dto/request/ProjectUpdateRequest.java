package com.group1.projectmanagementapi.project.models.dto.request;

import com.group1.projectmanagementapi.project.models.Project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequest {

    private Long id;
    private String title;
    
    public Project convertToEntity() {
        return Project.builder()
                .id(this.id)
                .title(this.title)
                .build();
    }
    
}
