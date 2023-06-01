package com.group1.projectmanagementapi.project;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.project.models.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project findOneById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));
    }

    public Project createOne(Project project) {
        return this.projectRepository.save(project);
    }

    public Project updateOne(Long id, Project project, Customer customer) {
        Project existingProject = this.findOneById(id);

        existingProject.setTitle(project.getTitle());
        
        customer.getProjects().add(existingProject);
        existingProject.getProjectMembers().add(customer);

        return this.projectRepository.save(existingProject);
    }

    public void deleteOne(Long id) {
        Project project = this.findOneById(id);

        // Hapus referensi Project dari daftar projects pada Customer
        // Customer customer = project.getCustomer();
        // if (customer != null) {
        //     customer.getProjects().remove(project);
        // }
        
        projectRepository.delete(project);
    }
}
