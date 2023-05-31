package com.group1.projectmanagementapi.customer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.customer.models.Customer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Customer createOne(Customer customer) {
        ApplicationUser applicationUser = customer.getApplicationUser();
        String hashPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(hashPassword);

        return this.customerRepository.save(customer);
    }
    
}
