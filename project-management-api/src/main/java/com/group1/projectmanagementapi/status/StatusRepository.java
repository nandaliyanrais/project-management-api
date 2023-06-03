package com.group1.projectmanagementapi.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group1.projectmanagementapi.status.models.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByStatus(String status);

    @Query(value = "SELECT MAX(s.id) FROM Status s")
    Long getLastStatusId();

}
