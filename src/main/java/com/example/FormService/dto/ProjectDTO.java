package com.example.FormService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProjectDTO(
        Long id,

        @NotBlank(message = "Project name is required")
        @Size(max = 50, message = "Project name must not exceed 50 characters")
        String name,

        @Size(max = 200, message = "Project description must not exceed 200 characters")
        String description,

        List<Long> evaluationFormIds
) {}
