package com.example.FormService.dto;

import java.math.BigDecimal;

public record AnswerOptionDto(
        Long id,
        Long factorId,
        Integer value,
        String label,
        Boolean isPassing,
        BigDecimal weight
) {}
