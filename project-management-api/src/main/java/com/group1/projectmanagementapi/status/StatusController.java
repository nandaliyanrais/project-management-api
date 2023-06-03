package com.group1.projectmanagementapi.status;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.status.models.Status;
import com.group1.projectmanagementapi.status.models.dto.response.StatusResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/getAllStatuses")
    public ResponseEntity<List<StatusResponse>> getAllStatuses() {
        List<Status> statuses = statusService.getAllStatuses();
        List<StatusResponse> statusResponses = statuses.stream().map(status -> status.convertToResponse()).toList();

        return ResponseEntity.ok(statusResponses);
    }
    
}
