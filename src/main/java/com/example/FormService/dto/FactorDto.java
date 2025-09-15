package com.example.FormService.dto;
import com.example.FormService.enums.AnswerType;

import java.math.BigDecimal;
import java.util.List;

public record FactorDto(
        Long id,
        Long categoryId,
        String questionText,
        BigDecimal weight,
        AnswerType answerType,
        String notes,
        String passAnswer,
        List<AnswerOptionDto> answerOptions
){}
