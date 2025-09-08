package com.example.FormService.dto;

import com.example.FormService.enums.CalculationMethod;
import com.example.FormService.enums.FormStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

public record EvaluationFormDTO(

        Long id,

        Long projectId,

        @NotBlank(message = "English name is required")
        @Size(max = 30, message = "English name must not exceed 30 characters")
        String nameEn,

        @NotBlank(message = "Arabic name is required")
        @Size(max = 30, message = "Arabic name must not exceed 30 characters")
        String nameAr,

        CalculationMethod calculationMethod,

        FormStatus status,

        Instant createdAt,
        Instant updatedAt,

        Integer supervisorId,

        List<Long> categoryIds,
        List<Long> successCriteriaIds
) {
        public EvaluationFormDTO(Long id, Long projectId, @NotBlank(message = "English name is required") @Size(max = 30, message = "English name must not exceed 30 characters") String nameEn, @NotBlank(message = "Arabic name is required") @Size(max = 30, message = "Arabic name must not exceed 30 characters") String nameAr, CalculationMethod calculationMethod, FormStatus status, Integer integer, List<Long> categoryIds, List<Long> successCriteriaIds) {
        this(id, projectId, nameEn, nameAr, calculationMethod, status, null, null, integer, categoryIds, successCriteriaIds);
        }
}
