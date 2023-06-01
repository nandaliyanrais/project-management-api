package com.group1.projectmanagementapi.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.customer.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String string);

    // Customer findByApplicationUser_Username(String username);
    
}
