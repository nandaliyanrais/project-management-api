package com.group1.projectmanagementapi.project;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.card.Card;
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
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("id") int id, @RequestBody ProjectRequest projectRequest) {
        Optional<Project> existingProject = this.projectService.getProjectById(id);

        if (existingProject.isPresent()) {
            Project project = projectRequest.convertToEntity();
            existingProject.get().setName(project.getName());
            existingProject.get().setProjectMembers(project.getProjectMembers());

            Project saveProject = this.projectService.postProject(existingProject.get());
            ProjectResponse  projectResponse = saveProject.convertToResponse();

            return ResponseEntity.ok().body(projectResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable("id") int id) {
        this.projectService.deleteProject(id);
    }

    @GetMapping("/projects/{id}/Tasks")
    public ResponseEntity<List<Card>> getAllTasks(@PathVariable("id") int id) {
        Optional<Project> existingProject = this.projectService.getProjectById(id);

    

        if (existingProject.isPresent()) {
            List<Card> taskLists = existingProject.get().getCards();
            return ResponseEntity.ok().body(taskLists);
        }

        return ResponseEntity.notFound().build();
    }

}
