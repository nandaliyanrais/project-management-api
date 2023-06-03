package com.group1.projectmanagementapi.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group1.projectmanagementapi.project.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "select distinct(s.status) from status s join task t on s.id = t.status_id join project p on p.id = t.project_id where p.id = ?1", nativeQuery = true)
    List<String> getAllProjectStatus(Long id);
}
