package com.example.FormService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record CategoryDto(
    Long id,
    @NotNull Long formId,
    @NotNull @Size(max = 30) String title,
    @NotNull BigDecimal weight,
    @NotNull Long severityId,
    List<FactorDto> factors
) {}
