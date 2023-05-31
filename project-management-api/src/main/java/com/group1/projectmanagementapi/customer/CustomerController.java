package com.group1.projectmanagementapi.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.customer.models.dto.request.CustomerRequest;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerRegisterResponse;
import com.group1.projectmanagementapi.customer.models.dto.response.CustomerResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegisterResponse> createOne(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer newCustomer = customerRequest.convertToEntity();
        Customer saveCustomer = this.customerService.createOne(newCustomer);
        CustomerResponse response = saveCustomer.convertToResponse();

        CustomerRegisterResponse registerResponse = CustomerRegisterResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer registered successfully")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
    
}
