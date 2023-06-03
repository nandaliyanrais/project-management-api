package com.group1.projectmanagementapi.project.models.dto.request;

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
