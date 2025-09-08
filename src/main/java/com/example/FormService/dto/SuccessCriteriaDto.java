package com.example.FormService.dto;

import jakarta.validation.constraints.NotNull;

public record SuccessCriteriaDto(
    Long id,
    @NotNull Long formId,
    @NotNull Long severityId,
    @NotNull Double threshold
) {}
