package com.example.FormService.repository;


import com.example.FormService.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findByNameIgnoreCase(String name);
}
