package com.group1.projectmanagementapi.customer.models.dto.response;

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
public class CustomerMessageResponse {

    private int status;
    private String message;
    private Object data;
    
}
