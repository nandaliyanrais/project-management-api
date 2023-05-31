package com.group1.projectmanagementapi.project;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.project.model.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project postProject(Project project) {
        return this.projectRepository.save(project);
    }

    public Optional<Project> getProjectById(int id) {
        return this.projectRepository.findById(id);
    }

    public void deleteProject(int id) {
        this.projectRepository.deleteById(id);
    }

}
