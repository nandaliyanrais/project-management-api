package com.group1.projectmanagementapi.project.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.group1.projectmanagementapi.customer.Customer;
import com.group1.projectmanagementapi.project.model.Project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private Customer customer;

    public Project convertToEntity() {
        Project projectEntity = Project.builder().name(this.name).build(); 

        List<Customer> projectMembers = projectEntity.getProjectMembers();
        if (projectMembers == null) {
            List<Customer> newListProjectMembers = new ArrayList<>();
            newListProjectMembers.add(this.customer);

            projectEntity.setProjectMembers(newListProjectMembers);
            return projectEntity;
        }

        projectMembers.add(this.customer);
        projectEntity.setProjectMembers(projectMembers);
        return projectEntity;
    }
}
