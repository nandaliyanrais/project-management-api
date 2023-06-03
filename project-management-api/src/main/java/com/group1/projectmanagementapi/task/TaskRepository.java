package com.group1.projectmanagementapi.task;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.task.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    void deleteByProjectId(Long projectId);

}
