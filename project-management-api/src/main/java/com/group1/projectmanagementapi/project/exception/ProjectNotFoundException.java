package com.group1.projectmanagementapi.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Project not found")
public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException() {
        
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
