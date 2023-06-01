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

    @NotEmpty(message = "Username is required")
    private String username;
    
    // private Customer customer;

    public Project convertToEntity() {
        return Project.builder()
                .id(this.id)
                .title(this.title)
                .build();
    }

    // public Project convertToEntity() {

    //     Project projectEntity = Project.builder().title(this.title).build(); 

    //     List<Customer> projectMembers = projectEntity.getProjectMembers();
    //     if (projectMembers == null) {
    //         List<Customer> newListProjectMembers = new ArrayList<>();
    //         newListProjectMembers.add(this.customer);

    //         projectEntity.setProjectMembers(newListProjectMembers);
    //         return projectEntity;
    //     }

    //     projectMembers.add(this.customer);
    //     projectEntity.setProjectMembers(projectMembers);
    //     return projectEntity;
    // }
}
