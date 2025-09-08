package com.example.FormService.repository;

import com.example.quality_management_service.form.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {

}
