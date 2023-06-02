package com.group1.projectmanagementapi.task;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.task.models.Task;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    // private final ProjectService projectService;

    public Task findOneById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Task with id = " + id));
    }

    public Task createOne(Task task) {
        return this.taskRepository.save(task);
    }

    public Task updateOne(Task task) {
        Task existingTask = this.findOneById(task.getId());

        Project existingProject = existingTask.getProject();

        Optional.ofNullable(task.getTitle()).ifPresent(existingTask::setTitle);
        Optional.ofNullable(task.getDescription()).ifPresent(existingTask::setDescription);
        Optional.ofNullable(task.getStatus()).ifPresent(existingTask::setStatus);
        // Optional.ofNullable(task.getProjects()).ifPresent(existingTask::setProjects);

        if (existingTask.getProject() == null) {
            existingTask.setProject(existingProject);
        }

        Task updatedTask = this.taskRepository.save(existingTask);

        return updatedTask;
    }

    public void deleteOne(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Task with id = " + id));

        Project project = task.getProject();
        if (project != null) {
            project.getTasks().remove(task);
        }

        taskRepository.delete(task);
    }

    @Transactional
    public void deleteTasksByProjectId(Long projectId) {
        taskRepository.deleteByProjectId(projectId);
    }

}
