package com.group1.projectmanagementapi.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.customer.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
