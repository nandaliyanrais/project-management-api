package com.group1.projectmanagementapi.applicationuser;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {
    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);
}
