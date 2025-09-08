package com.example.FormService.mapper;

import com.example.FormService.dto.FactorDto;
import com.example.FormService.model.Factor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FactorMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "passAnswer", target = "passAnswer")
    FactorDto toDto(Factor factor);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "passAnswer", target = "passAnswer")
    Factor toEntity(FactorDto factorDTO);
}