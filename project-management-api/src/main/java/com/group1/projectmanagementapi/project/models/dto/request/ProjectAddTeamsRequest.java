package com.group1.projectmanagementapi.project.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAddTeamsRequest {

    private Long id;
    private String usernameOrEmail;
    
}
