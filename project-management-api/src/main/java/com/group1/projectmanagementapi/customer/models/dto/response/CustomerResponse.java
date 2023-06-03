package com.group1.projectmanagementapi.customer.models.dto.response;



import java.sql.Timestamp;
import java.util.List;

import com.group1.projectmanagementapi.project.models.dto.response.ProjectListResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String imageUrl;
    private List<ProjectListResponse> projects;
    private Timestamp createdAt;
    
}
