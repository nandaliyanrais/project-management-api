package com.group1.projectmanagementapi.status;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group1.projectmanagementapi.exception.ResourceNotFoundException;
import com.group1.projectmanagementapi.status.models.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Status findOneById(Long id) {
        return this.statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Status with id = " + id));
    }

    public Optional<Status> findOneByStatus(String status) {
        return this.statusRepository.findByStatus(status);
    }

    public Status createOne(Status status) {
        return this.statusRepository.save(status);
    }

    public List<Status> getAllStatuses() {
        return this.statusRepository.findAll();
    }

}
