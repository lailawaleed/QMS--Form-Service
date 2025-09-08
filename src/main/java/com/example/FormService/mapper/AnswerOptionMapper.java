package com.example.FormService.mapper;

import com.example.FormService.dto.AnswerOptionDto;
import com.example.FormService.model.AnswerOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {

    @Mappings({
            @Mapping(source = "factor.id", target = "factorId"),
            @Mapping(source = "passing", target = "isPassing") // keep the same name for clarity
    })
    AnswerOptionDto toDto(AnswerOption entity);

    @Mappings({
            @Mapping(source = "factorId", target = "factor.id"),
            @Mapping(source = "isPassing", target = "passing")
    })
    AnswerOption toEntity(AnswerOptionDto dto);
}
