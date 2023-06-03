package com.group1.projectmanagementapi.task;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.authentication.models.UserPrincipal;
import com.group1.projectmanagementapi.customer.CustomerService;
import com.group1.projectmanagementapi.customer.models.Customer;
import com.group1.projectmanagementapi.project.ProjectService;
import com.group1.projectmanagementapi.project.models.Project;
import com.group1.projectmanagementapi.task.models.Task;
import com.group1.projectmanagementapi.task.models.dto.request.TaskRequest;
import com.group1.projectmanagementapi.task.models.dto.request.TaskUpdateRequest;
import com.group1.projectmanagementapi.task.models.dto.response.TaskResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Task")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final CustomerService customerService;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createOne(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody TaskRequest taskRequest,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        Project project = projectService.findOneById(projectId);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = project.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        Task newTask = taskRequest.convertToEntity();
        newTask.setProject(project);
        Task savedTask = this.taskService.createOne(newTask);
        TaskResponse response = savedTask.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<Task> tasks = this.taskService.getAllTasks();
        List<TaskResponse> taskResponses = tasks.stream().map(task -> task.convertToResponse()).toList();

        return ResponseEntity.ok().body(taskResponses);
    }
        
    @GetMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        Project project = projectService.findOneById(projectId);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = project.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        Task existingTask = this.taskService.findOneById(taskId);
        TaskResponse taskResponse = existingTask.convertToResponse();

        return ResponseEntity.ok().body(taskResponse);
    }

    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @Valid @RequestBody TaskUpdateRequest taskRequest,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        Project project = projectService.findOneById(projectId);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = project.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        Task task = taskRequest.convertToEntity();
        // task.setId(taskId);
        Task updatedTask = this.taskService.updateOne(taskId, task);

        return ResponseEntity.ok().body(updatedTask.convertToResponse());
    }

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        Project project = projectService.findOneById(projectId);

        Customer customerLogin = this.customerService.findOneByUsername(currentUser.getUsername());
        List<Customer> projectMembers = project.getProjectMembers();

        if (!projectMembers.contains(customerLogin)) {
            throw new AccessDeniedException("You can't access this");
        }

        this.taskService.deleteOne(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

}
