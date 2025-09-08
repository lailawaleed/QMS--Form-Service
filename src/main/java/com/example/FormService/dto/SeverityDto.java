package com.example.FormService.dto;

import java.math.BigDecimal;

public record SeverityDto(
        Long id,
        String name,
        String description,
        BigDecimal threshold
) {}

