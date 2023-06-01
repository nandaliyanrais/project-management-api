package com.group1.projectmanagementapi.project.models.dto.response;

import java.sql.Timestamp;

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
public class ProjectCreateResponse {

    private Long id;
    private String title;
    private Timestamp createdAt;
    
}
