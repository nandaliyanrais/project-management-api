package com.group1.projectmanagementapi.project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.card.models.Card;
import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.project.model.Project;
import com.group1.projectmanagementapi.project.model.dto.ProjectRequest;
import com.group1.projectmanagementapi.project.model.dto.ProjectResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        Project newProject = projectRequest.convertToEntity();
        Project saveProject = this.projectService.postProject(newProject);
        ProjectResponse projectResponse = saveProject.convertToResponse();

        return ResponseEntity.created(null).body(projectResponse);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("id") int id,
            @RequestBody ProjectRequest projectRequest) {
        Project existingProject = this.projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));

        Project project = projectRequest.convertToEntity();
        existingProject.setName(project.getName());
        existingProject.setProjectMembers(project.getProjectMembers());

        Project saveProject = this.projectService.postProject(existingProject);
        ProjectResponse projectResponse = saveProject.convertToResponse();

        return ResponseEntity.ok().body(projectResponse);

    }

    @DeleteMapping("/projects/{id}")
    // @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<Project> deleteProject(@PathVariable("id") int id) {
        Project existingProject = this.projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));

        this.projectService.deleteProject(id);
        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/projects/{id}/tasks")
    public ResponseEntity<List<Card>> getAllTasks(@PathVariable("id") int id) {
        Project existingProject = this.projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));

        List<Card> taskLists = existingProject.getCards();
        return ResponseEntity.ok().body(taskLists);

    }

}
