package com.group1.projectmanagementapi.customer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.project.ProjectRepository;
import com.group1.projectmanagementapi.project.models.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProjectRepository projectRepository;

    public Customer findOneById(Long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found customer with id = " + id));
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

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer updateOne(Customer customer) {
        Customer existingCustomer = this.findOneById(customer.getId());

        Optional.ofNullable(customer.getName()).ifPresent(existingCustomer::setName);
        Optional.ofNullable(customer.getUsername()).ifPresent(existingCustomer::setUsername);
        Optional.ofNullable(customer.getEmail()).ifPresent(existingCustomer::setEmail);
        Optional.ofNullable(customer.getImageUrl()).ifPresent(existingCustomer::setImageUrl);

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

    // public void removeProjectFromCustomer(Long customerId, Long projectId) {
    // Customer customer = customerRepository.findById(customerId)
    // .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id:
    // " + customerId));

    // Project project = projectRepository.findById(projectId)
    // .orElseThrow(() -> new ResourceNotFoundException("Project not found with id:
    // " + projectId));

    // customer.getProjects().remove(project);
    // customerRepository.save(customer);
    // }

    @Transactional
    public void removeProjectFromCustomer(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        List<Customer> customers = customerRepository.findByProjects_Id(projectId);
        customers.forEach(customer -> customer.getProjects().remove(project));
        customerRepository.deleteByProjectsIn(Collections.singletonList(project));
    }
}
