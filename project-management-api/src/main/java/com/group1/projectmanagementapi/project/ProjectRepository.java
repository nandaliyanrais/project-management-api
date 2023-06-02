package com.group1.projectmanagementapi.project;


import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.project.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
