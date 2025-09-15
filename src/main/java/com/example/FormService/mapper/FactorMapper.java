package com.example.FormService.mapper;

import com.example.FormService.dto.FactorDto;
import com.example.FormService.model.Factor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.Named;
import com.example.FormService.model.AnswerOption;
import com.example.FormService.dto.AnswerOptionDto;

@Mapper(componentModel = "spring")
public interface FactorMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "passAnswer", target = "passAnswer")
    @Mapping(source = "answerOptions", target = "answerOptions", qualifiedByName = "mapAnswerOptions")
    FactorDto toDto(Factor factor);

    @Named("mapAnswerOptions")
    default List<AnswerOptionDto> mapAnswerOptions(List<AnswerOption> answerOptions) {
        if (answerOptions == null) return null;
        AnswerOptionMapper mapper = org.mapstruct.factory.Mappers.getMapper(AnswerOptionMapper.class);
        return answerOptions.stream().map(mapper::toDto).toList();
    }

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "passAnswer", target = "passAnswer")
    Factor toEntity(FactorDto factorDTO);
}