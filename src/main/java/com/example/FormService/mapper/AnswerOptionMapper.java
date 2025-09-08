package com.example.FormService.mapper;

import com.example.FormService.dto.AnswerOptionDto;
import com.example.FormService.model.AnswerOption;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {

    @Mapping(source = "factor.id", target = "factorId")
    @Mapping(source = "passing", target = "isPassing")
    AnswerOptionDto toDto(AnswerOption entity);

    @Mapping(source = "factorId", target = "factor.id")
    @Mapping(source = "isPassing", target = "passing")
    AnswerOption toEntity(AnswerOptionDto dto);
}
