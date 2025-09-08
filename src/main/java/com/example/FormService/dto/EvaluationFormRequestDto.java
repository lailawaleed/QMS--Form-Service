// EvaluationFormRequestDto.java
package com.example.FormService.dto;

import com.example.FormService.enums.CalculationMethod;

import java.util.List;

public record EvaluationFormRequestDto(
        Long projectId,
        String nameEn,
        String nameAr,
        CalculationMethod calculationMethod,
        List<CategoryRequestDto> categories
) {}
