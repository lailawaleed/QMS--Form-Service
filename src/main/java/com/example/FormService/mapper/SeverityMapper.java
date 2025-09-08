package com.example.FormService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SeverityMapper {

    SeverityMapper INSTANCE = Mappers.getMapper(SeverityMapper.class);

    SeverityDto toDto(Severity severity);

    Severity toEntity(SeverityDto dto);
}
