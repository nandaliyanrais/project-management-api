package com.group1.projectmanagementapi.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
            
        Customer customer = customerUpdateRequest.convertToEntity();
        customer.setId(id);
        Customer updateCustomer = this.customerService.updateOne(customer);

        return ResponseEntity.status(HttpStatus.OK).body(updateCustomer.convertToUpdateResponse());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<CustomerResponse> getOne(@PathVariable("userId") Long id) {
        Customer existingCustomer = this.customerService.findOneById(id);
        CustomerResponse response = existingCustomer.convertToResponse();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<List<ProjectListResponse>> getAllUserProjects(@PathVariable("userId") Long id) {
        Customer findCustomer = this.customerService.findOneById(id);
        List<Project> projects = findCustomer.getProjects();
        List<ProjectListResponse> projectListResponses = projects.stream().map(project -> project.convertToListResponse()).toList();

        return ResponseEntity.status(HttpStatus.OK).body(projectListResponses);
    }
}
