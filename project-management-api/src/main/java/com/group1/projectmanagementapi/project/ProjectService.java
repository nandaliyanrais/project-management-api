package com.group1.projectmanagementapi.project;

import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.exception.ProjectNotFoundException;
import com.group1.projectmanagementapi.project.models.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project findOneById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException());
    }

    public Project createOne(Project project) {
        return this.projectRepository.save(project);
    }

    public Project updateOne(Project project) {
        Project existingProject = this.findOneById(project.getId());

        Customer existingCustomer = existingProject.getCustomer();

        existingProject.setTitle(project.getTitle());
        existingProject.setCustomer(project.getCustomer());

        if (existingProject.getCustomer() == null) {
            existingProject.setCustomer(existingCustomer);
        }

        Project updatedProject = this.projectRepository.save(existingProject);

        return updatedProject;
    }

    public void deleteOne(Long id) {
        Project project = projectRepository.findById(id)
        .orElseThrow(() -> new ProjectNotFoundException());

        // Hapus referensi Project dari daftar projects pada Customer
        Customer customer = project.getCustomer();
        if (customer != null) {
            customer.getProjects().remove(project);
        }
        
        projectRepository.delete(project);
    }



    // public Project postProject(Project project) {
    //     return this.projectRepository.save(project);
    // }

    // public Optional<Project> getProjectById(int id) {
    //     return this.projectRepository.findById(id);
    // }

    // public void deleteProject(int id) {
    //     this.projectRepository.deleteById(id);
    // }

}
