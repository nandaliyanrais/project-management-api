package com.group1.projectmanagementapi.task;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.status.StatusService;
import com.group1.projectmanagementapi.status.models.Status;
import com.group1.projectmanagementapi.task.models.Task;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final StatusService statusService;

    public Task findOneById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Task with id = " + id));
    }

    public Task createOne(Task task) {
        Status newStatus = new Status(task.getStatus().getStatus().toUpperCase());
        Status existingStatus = statusService.findOneByStatus(newStatus.getStatus());
        Status status = null;

        if (existingStatus != null) {
            status = existingStatus;
        } else {
            statusService.createOne(newStatus);
            status = newStatus;
        }
        Task tasks = Task.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .project(task.getProject())
                .status(status)
                .build();
        return taskRepository.save(tasks);
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
