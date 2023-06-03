package com.group1.projectmanagementapi.customer.models.dto.request;

import com.group1.projectmanagementapi.customer.models.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String imageUrl;

    public Customer convertToEntity() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .build();
    }

}
