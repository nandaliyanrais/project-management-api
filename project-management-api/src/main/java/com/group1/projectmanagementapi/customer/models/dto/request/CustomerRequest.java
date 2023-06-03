package com.group1.projectmanagementapi.customer.models.dto.request;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.customer.models.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;
    

    public Customer convertToEntity() {
        Customer customer = Customer.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .build();
        ApplicationUser applicationUser = ApplicationUser.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(password)
                .build();
        
        customer.setApplicationUser(applicationUser);
        applicationUser.setCustomer(customer);

        return customer;
    }
    
}
