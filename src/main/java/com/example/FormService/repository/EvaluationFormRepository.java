package com.example.FormService.repository;

import com.example.FormService.model.EvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationFormRepository extends JpaRepository<EvaluationForm,Long> {
    Optional<EvaluationForm> findByNameEnIgnoreCase(String nameEn);
    Optional<EvaluationForm> findByNameArIgnoreCase(String nameAr);
}
