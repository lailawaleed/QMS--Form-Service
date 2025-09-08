package com.example.FormService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SuccessCriteriaMapper {
    SuccessCriteriaMapper INSTANCE = Mappers.getMapper(SuccessCriteriaMapper.class);
    SuccessCriteriaDto toDto(SuccessCriteria entity);
    SuccessCriteria toEntity(SuccessCriteriaDto dto);
}

