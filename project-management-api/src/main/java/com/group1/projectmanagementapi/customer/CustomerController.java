package com.group1.projectmanagementapi.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.authentication.models.UserPrincipal;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.customer.models.dto.request.CustomerRequest;
import com.group1.projectmanagementapi.customer.models.dto.request.CustomerUpdateRequest;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerCreateResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerMessageResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerUpdateResponse;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectListResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Register user")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerMessageResponse> createOne(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer newCustomer = customerRequest.convertToEntity();
        Customer saveCustomer = this.customerService.createOne(newCustomer);
        CustomerCreateResponse response = saveCustomer.convertToCreateResponse();

        CustomerMessageResponse registerResponse = CustomerMessageResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer registered successfully")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<CustomerUpdateResponse> updateOne(
        @PathVariable("userId") Long id,
        @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest,
        @AuthenticationPrincipal UserPrincipal currentUser) {

        Customer existingCustomer = this.customerService.findOneById(id);
        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());

        if (!existingCustomer.getUsername().equals(customerLogin.getUsername())) {
            throw new AccessDeniedException("You can't access this");
        }

        Customer customer = customerUpdateRequest.convertToEntity();
        customer.setId(id);
        Customer updatedCustomer = this.customerService.updateOne(customer);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer.convertToUpdateResponse());
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<CustomerUpdateResponse>> getAllCustomers() {
        List<Customer> customers = this.customerService.getAllCustomers();
        List<CustomerUpdateResponse> response = customers.stream().map(customer -> customer.convertToUpdateResponse()).toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<CustomerResponse> getOne(@PathVariable("userId") Long id,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        Customer existingCustomer = this.customerService.findOneById(id);
        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());

        if (!existingCustomer.getUsername().equals(customerLogin.getUsername())) {
            throw new AccessDeniedException("You can't access this");
        }

        CustomerResponse response = existingCustomer.convertToResponse();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<List<ProjectListResponse>> getAllUserProjects(
            @PathVariable("userId") Long id,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        Customer existingCustomer = this.customerService.findOneById(id);
        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());

        if (!existingCustomer.getUsername().equals(customerLogin.getUsername())) {
            throw new AccessDeniedException("You can't access this");
        }

        List<Project> projects = existingCustomer.getProjects();
        List<ProjectListResponse> projectListResponses = projects.stream()
                .map(project -> project.convertToListResponse()).toList();

        return ResponseEntity.status(HttpStatus.OK).body(projectListResponses);
    }
}
