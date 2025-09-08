package com.example.FormService.mapper;

import com.example.quality_management_service.form.dto.SeverityDto;
import com.example.quality_management_service.form.model.Severity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SeverityMapper {

    SeverityMapper INSTANCE = Mappers.getMapper(SeverityMapper.class);

    SeverityDto toDto(Severity severity);

    Severity toEntity(SeverityDto dto);
}
