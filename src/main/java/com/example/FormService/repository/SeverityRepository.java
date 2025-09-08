package com.example.FormService.repository;

import com.example.FormService.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeverityRepository extends JpaRepository<Severity, Long> {
}

