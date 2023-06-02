package com.group1.projectmanagementapi.project;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.customer.CustomerService;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.project.models.dto.request.ProjectRequest;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectResponse;
import com.group1.projectmanagementapi.task.TaskService;
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

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest projectRequest) {

        Customer customer = customerService.findOneByUsername(projectRequest.getProjectMember());
        Project newProject = projectRequest.convertToEntity();

        customer.getProjects().add(newProject);
        newProject.getProjectMembers().add(customer);

        Project saveProject = projectService.createOne(newProject);
        ProjectResponse projectResponse = saveProject.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("projectId") Long id,
            @RequestParam(name = "status", required = false) Optional<String> status) {
        Project existingProject = this.projectService.findOneById(id);
        ProjectResponse projectResponse = existingProject.convertToResponse();

        return ResponseEntity.ok().body(projectResponse);
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("projectId") Long id,
            @Valid @RequestBody ProjectRequest projectRequest) {

        Customer customer = this.customerService.findOneByUsername(projectRequest.getProjectMember());
        Project project = projectRequest.convertToEntity();

        Project updatedProject = this.projectService.updateOne(id, project, customer);
        
        return ResponseEntity.ok().body(updatedProject.convertToResponse());
    }

    @DeleteMapping("/projects/{projectId}")
    // @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long id) {
        this.customerService.removeProjectFromCustomer(id);
        this.taskService.deleteTasksByProjectId(id);
        this.projectService.deleteOne(id);
        return ResponseEntity.ok("Project deleted successfully");
    }

    @GetMapping("/projects/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@PathVariable("id") Long id) {
        Project existingProject = this.projectService.findOneById(id);
        List<TaskResponse> taskLists = existingProject.getTasks().stream().map(task -> task.convertToResponse())
                .toList();
        return ResponseEntity.ok().body(taskLists);
    }

}
