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
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.customer.CustomerService;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.project.models.dto.request.ProjectRequest;
import com.group1.projectmanagementapi.project.models.dto.request.ProjectUpdateRequest;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectCreateResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectResponse;
import com.group1.projectmanagementapi.project.models.dto.response.ProjectUpdateResponse;

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

    @PostMapping("/users/{userId}/projects")
    public ResponseEntity<ProjectCreateResponse> createProject(
        @PathVariable("userId") Long userId, 
        @Valid @RequestBody ProjectRequest projectRequest) {

        Customer customer = customerService.findOneById(userId);
    
        Project newProject = projectRequest.convertToEntity();
        newProject.setCustomer(customer);
        Project saveProject = projectService.createOne(newProject);
        ProjectCreateResponse projectResponse = saveProject.convertToCreateResponse();
    
        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("projectId") Long id) {
        Project existingProject = this.projectService.findOneById(id);
        ProjectResponse projectResponse = existingProject.convertToResponse();

        return ResponseEntity.ok().body(projectResponse);
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectUpdateResponse> updateProject(
        @PathVariable("projectId") Long id, 
        @Valid @RequestBody ProjectUpdateRequest projectRequest) {

        Project project = projectRequest.convertToEntity();
        project.setId(id);
        Project updatedProject = this.projectService.updateOne(project);

        return ResponseEntity.ok().body(updatedProject.convertToUpdateResponse());
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long id) {
        this.projectService.deleteOne(id);
        return ResponseEntity.ok("Project deleted successfully");
    }



    // @PutMapping("/projects/{id}")
    // public ResponseEntity<ProjectResponse> updateProject(@PathVariable("id") int id, @RequestBody ProjectRequest projectRequest) {
    //     Optional<Project> existingProject = this.projectService.getProjectById(id);

    //     if (existingProject.isPresent()) {
    //         Project project = projectRequest.convertToEntity();
    //         existingProject.get().setName(project.getName());
    //         existingProject.get().setProjectMembers(project.getProjectMembers());

    //         Project saveProject = this.projectService.postProject(existingProject.get());
    //         ProjectResponse  projectResponse = saveProject.convertToResponse();

    //         return ResponseEntity.ok().body(projectResponse);
    //     }

    //     return ResponseEntity.notFound().build();
    // }

    

    // @GetMapping("/projects/{id}/Tasks")
    // public ResponseEntity<List<Card>> getAllTasks(@PathVariable("id") int id) {
    //     Optional<Project> existingProject = this.projectService.getProjectById(id);

    

    //     if (existingProject.isPresent()) {
    //         List<Card> taskLists = existingProject.get().getCards();
    //         return ResponseEntity.ok().body(taskLists);
    //     }

    //     return ResponseEntity.notFound().build();
    // }

}
