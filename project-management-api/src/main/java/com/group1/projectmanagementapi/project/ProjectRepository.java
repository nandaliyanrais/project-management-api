package com.group1.projectmanagementapi.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.group1.projectmanagementapi.project.models.Project;

import jakarta.transaction.Transactional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from customer_project where project_id = ?1", nativeQuery = true)
    void deleteProjectMembers(Long id);
}
