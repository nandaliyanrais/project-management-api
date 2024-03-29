package com.group1.projectmanagementapi.authentication.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDto {

    private String name;
    private String username;
    private String email;
    private String token;
    
    // public JwtResponseDto(String token) {
    //     this.token = token;
    // }
}