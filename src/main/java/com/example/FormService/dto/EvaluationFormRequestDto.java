// EvaluationFormRequestDto.java
package com.example.FormService.dto;

import com.example.FormService.enums.CalculationMethod;
import com.example.FormService.enums.FormStatus;

import java.util.List;

public record EvaluationFormRequestDto(
        Long projectId,
        String nameEn,
        String nameAr,
        CalculationMethod calculationMethod,
        List<CategoryRequestDto> categories
) {
    public FormStatus status() {
        return FormStatus.ACTIVE;
    }

    public Integer supervisorId() {
        return 1; // Placeholder for actual supervisor ID retrieval logic
    }

    public Object successCriteria() {
        return List.of(); // Placeholder for actual success criteria retrieval logic
    }
}
