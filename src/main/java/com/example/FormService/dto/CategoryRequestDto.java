// CategoryRequestDto.java
package com.example.FormService.dto;

import java.math.BigDecimal;
import java.util.List;

public record CategoryRequestDto(
        String title,
        BigDecimal weight,
        Long severityId,
        List<FactorRequestDto> factors
) {}
