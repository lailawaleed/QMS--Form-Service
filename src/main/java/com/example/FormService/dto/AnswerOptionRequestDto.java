package com.example.FormService.dto;

import java.math.BigDecimal;

public record AnswerOptionRequestDto(
        Integer value,
        String label,
        Boolean isPassing,
        BigDecimal weight
) {}
