package com.group1.projectmanagementapi.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.exception.MissingServletRequestParameterException;
import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.task.models.Task;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project findOneById(Long id) {
        return this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));
    }

    public Project createOne(Project project) {
        return this.projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    // public Project updateOne(Long id, Project project, Customer customer) {
    // Project existingProject = this.findOneById(id);

    // if (existingProject.getProjectMembers().contains(customer)) {
    // throw new MissingServletRequestParameterException("User already in this
    // project");
    // }

    // existingProject.setTitle(project.getTitle());

    // customer.getProjects().add(existingProject);
    // existingProject.getProjectMembers().add(customer);

    // return this.projectRepository.save(existingProject);
    // }

    public Project updateOne(Long id, Project project, Customer customer) {
        Project existingProject = this.findOneById(id);

        if (existingProject.getProjectMembers().contains(customer)) {
            throw new MissingServletRequestParameterException("User already in this project");
        }

        if (project.getTitle() != null) {
            existingProject.setTitle(project.getTitle());
        }

        if (customer != null) {
            customer.getProjects().add(existingProject);
            existingProject.getProjectMembers().add(customer);
        }

        return this.projectRepository.save(existingProject);
    }

    public void deleteOne(Long id) {
        Project project = this.findOneById(id);

        // Hapus referensi Project dari daftar projects pada Customer
        List<Customer> customer = project.getProjectMembers();
        if (customer != null) {
            customer.stream().map(cust -> cust.getProjects().remove(project));
        }

        projectRepository.delete(project);
    }

    public List<Task> getAllTasks(Project project, Optional<String> status) {
        List<Task> taskLists = project.getTasks();

        if (status.isPresent() && !taskLists.isEmpty()) {
            List<Task> taskFiltered = taskLists.stream()
                    .filter(task -> task.getStatus().getStatus().equals(status.get().toUpperCase())).toList();

            return taskFiltered;
        }

        return taskLists;
    }

    public List<String> getProjectStatus(Long id) {
        List<String> statusList = new ArrayList<>();
        statusList.add("TO_DO");
        statusList.add("IN_DEV");
        statusList.add("DONE");

        List<String> getStatus = this.projectRepository.getAllProjectStatus(id);
        List<String> filteredStatus = getStatus.stream()
                .filter(stat -> !stat.equalsIgnoreCase("TO_DO")
                        && !stat.equalsIgnoreCase("IN_DEV")
                        && !stat.equalsIgnoreCase("DONE"))
                .toList();

        if (!filteredStatus.isEmpty()) {
            statusList.addAll(filteredStatus);
            return statusList;
        }

        return statusList;
    }
}
