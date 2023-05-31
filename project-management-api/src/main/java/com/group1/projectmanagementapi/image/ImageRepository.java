package com.group1.projectmanagementapi.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.projectmanagementapi.image.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public Optional<Image> findByName(String name);
    
}
