package com.group1.projectmanagementapi.customer;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.customer.exception.CustomerNotFoundException;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Customer findOneById(Long id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException());
    }

    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer createOne(Customer customer) {
        ApplicationUser applicationUser = customer.getApplicationUser();
        String hashPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(hashPassword);

        return this.customerRepository.save(customer);
    }

    public Customer updateOne(Customer customer) {
        Customer existingCustomer = this.findOneById(customer.getId());

        Optional.ofNullable(customer.getName()).ifPresent(existingCustomer::setName);
        Optional.ofNullable(customer.getUsername()).ifPresent(existingCustomer::setUsername);
        Optional.ofNullable(customer.getEmail()).ifPresent(existingCustomer::setEmail);

        ApplicationUser applicationUser = existingCustomer.getApplicationUser();
        if (applicationUser != null) {
            Optional.ofNullable(customer.getName()).ifPresent(applicationUser::setName);
            Optional.ofNullable(customer.getUsername()).ifPresent(applicationUser::setUsername);
            Optional.ofNullable(customer.getEmail()).ifPresent(applicationUser::setEmail);
        }

        Customer updatedCustomer = this.customerRepository.save(existingCustomer);

        return updatedCustomer;
    }

    public Customer findOneByUsername(String username) {

        return this.customerRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
                "Not found customer with username= " + username));
    }
}
