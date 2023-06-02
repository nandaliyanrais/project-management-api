package com.group1.projectmanagementapi.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.models.Project;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String string);

    List<Customer> findByProjects_Id(Long projectId);

    void deleteByProjectsIn(List<Project> projects);

    // Customer findByApplicationUser_Username(String username);

}
