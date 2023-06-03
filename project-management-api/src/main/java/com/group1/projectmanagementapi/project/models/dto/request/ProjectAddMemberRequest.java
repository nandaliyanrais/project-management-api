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
public class ProjectAddMemberRequest {

    private Long id;
    private String title;
    private String addProjectMember;

}
