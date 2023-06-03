package com.group1.projectmanagementapi.project;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.authentication.models.UserPrincipal;
import com.group1.projectmanagementapi.customer.CustomerService;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.project.models.dto.request.ProjectAddMemberRequest;
import com.group1.projectmanagementapi.project.models.dto.request.ProjectRequest;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectResponse;
import com.group1.projectmanagementapi.task.TaskService;
import com.group1.projectmanagementapi.task.models.Task;
import com.group1.projectmanagementapi.task.models.dto.response.TaskResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Project")
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final CustomerService customerService;
    private final TaskService taskService;

    // @PostMapping("/projects")
    // public ResponseEntity<ProjectResponse> createProject(
    // @Valid @RequestBody ProjectRequest projectRequest) {

    // Customer customer =
    // customerService.findOneByUsername(projectRequest.getProjectMember());
    // Project newProject = projectRequest.convertToEntity();

    // customer.getProjects().add(newProject);
    // newProject.getProjectMembers().add(customer);

    // Project saveProject = projectService.createOne(newProject);
    // ProjectResponse projectResponse = saveProject.convertToResponse();

    // return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    // }

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest projectRequest,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        String username = currentUser.getUsername();
        Customer customer = customerService.findOneByUsername(username);
        Project newProject = projectRequest.convertToEntity();

        newProject.getProjectMembers().add(customer);
        customer.getProjects().add(newProject);

        Project savedProject = projectService.createOne(newProject);
        ProjectResponse projectResponse = savedProject.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectResponse> projectResponses = projects.stream().map(project -> project.convertToResponse()).toList();

        return ResponseEntity.ok().body(projectResponses);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("projectId") Long id,
            @RequestParam(name = "status", required = false) Optional<String> status,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        Project existingProject = this.projectService.findOneById(id);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = existingProject.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        List<String> statuses = this.projectService.getProjectStatus(id);
        // List<StatusResponse> statusResponses = statuses.stream().map(stat ->
        // stat.convertToResponse()).toList();
        ProjectResponse projectResponse = existingProject.convertToResponse();
        projectResponse.setStatuses(statuses);

        return ResponseEntity.ok().body(projectResponse);
    }

    // @PutMapping("/projects/{projectId}")
    // public ResponseEntity<ProjectResponse> updateProject(
    // @PathVariable("projectId") Long id,
    // @Valid @RequestBody ProjectRequest projectRequest,
    // @AuthenticationPrincipal UserPrincipal currentUser) {

    // Customer customer =
    // this.customerService.findOneByUsername(projectRequest.getProjectMember());
    // Project project = projectRequest.convertToEntity();
    // Project existingProject = this.projectService.findOneById(id);

    // Customer customerLogin =
    // this.customerService.findOneByUsername(currentUser.getUsername());
    // List<Customer> projectMembers = existingProject.getProjectMembers();

    // if (!projectMembers.contains(customerLogin)) {
    // throw new AccessDeniedException("You can't access this");
    // }

    // Project updatedProject = this.projectService.updateOne(id, project,
    // customer);

    // return ResponseEntity.ok().body(updatedProject.convertToResponse());
    // }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("projectId") Long id,
            @Valid @RequestBody ProjectAddMemberRequest projectRequest,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        Project existingProject = this.projectService.findOneById(id);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = existingProject.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        Customer customer = null;
        if (projectRequest.getAddProjectMember() != null) {
            customer = this.customerService.findOneByUsername(projectRequest.getAddProjectMember());
            // if (customer == null) {
            // throw new ResourceNotFoundException("User not found!");
            // }
        }

        Project project = Project.builder()
                .id(id)
                .title(projectRequest.getTitle() != null ? projectRequest.getTitle() : existingProject.getTitle())
                .build();

        Project updatedProject = this.projectService.updateOne(id, project, customer);

        return ResponseEntity.ok().body(updatedProject.convertToResponse());
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long id,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        Project existingProject = this.projectService.findOneById(id);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = existingProject.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        this.customerService.removeProjectFromCustomer(id);
        this.taskService.deleteTasksByProjectId(id);
        this.projectService.deleteOne(id);
        return ResponseEntity.ok("Project deleted successfully");
    }

    @GetMapping("/projects/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@PathVariable("id") Long id,
            @RequestParam(name = "status") Optional<String> status,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        Project existingProject = this.projectService.findOneById(id);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = existingProject.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        List<Task> tasks = this.projectService.getAllTasks(existingProject, status);
        List<TaskResponse> taskResponses = tasks.stream().map(task -> task.convertToResponse()).toList();
        return ResponseEntity.ok().body(taskResponses);
    }

}
