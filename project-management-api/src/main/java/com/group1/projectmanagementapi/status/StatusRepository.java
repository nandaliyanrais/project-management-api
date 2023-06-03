package com.group1.projectmanagementapi.status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group1.projectmanagementapi.status.models.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByStatus(String status);

    @Query(value = "SELECT MAX(s.id) FROM Status s")
    Long getLastStatusId();

}
