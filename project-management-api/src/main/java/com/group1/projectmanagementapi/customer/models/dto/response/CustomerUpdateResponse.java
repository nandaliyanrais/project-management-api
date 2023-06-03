package com.group1.projectmanagementapi.customer.models.dto.response;

import java.sql.Timestamp;

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
public class CustomerUpdateResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String imageUrl;
    private Timestamp updatedAt;

}
