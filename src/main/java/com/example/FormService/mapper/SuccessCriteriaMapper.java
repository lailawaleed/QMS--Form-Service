package com.example.FormService.mapper;

import com.example.quality_management_service.form.dto.SuccessCriteriaDto;
import com.example.quality_management_service.form.model.SuccessCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SuccessCriteriaMapper {
    SuccessCriteriaMapper INSTANCE = Mappers.getMapper(SuccessCriteriaMapper.class);
    SuccessCriteriaDto toDto(SuccessCriteria entity);
    SuccessCriteria toEntity(SuccessCriteriaDto dto);
}

