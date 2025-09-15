package com.example.FormService.mapper;
import com.example.FormService.dto.EvaluationFormDTO;
import com.example.FormService.dto.CategoryDto;
import com.example.FormService.dto.SuccessCriteriaDto;
import com.example.FormService.model.Category;
import com.example.FormService.model.EvaluationForm;
import com.example.FormService.model.SuccessCriteria;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EvaluationFormMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategories")
    @Mapping(source = "successCriteria", target = "successCriteria", qualifiedByName = "mapSuccessCriteria")
    @Mapping(source = "nameEn", target = "nameEn")
    @Mapping(source = "nameAr", target = "nameAr")
    @Mapping(source = "calculationMethod", target = "calculationMethod")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    EvaluationFormDTO toDTO(EvaluationForm form);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "supervisor", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "successCriteria", ignore = true)
    EvaluationForm toEntity(EvaluationFormDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EvaluationFormDTO dto, @MappingTarget EvaluationForm entity);

    // Convert list of Category objects to their IDs
    @Named("mapCategoryIds")
    default List<Long> mapCategoryIds(List<Category> categories) {
        if (categories == null) return null;
        return categories.stream().map(Category::getId).collect(Collectors.toList());
    }

    // Convert list of SuccessCriteria objects to their IDs
    @Named("mapSuccessCriteriaIds")
    default List<Long> mapSuccessCriteriaIds(List<SuccessCriteria> criteria) {
        if (criteria == null) return null;
        return criteria.stream().map(SuccessCriteria::getId).collect(Collectors.toList());
    }

    @Named("mapCategories")
    default List<CategoryDto> mapCategories(List<Category> categories) {
        if (categories == null) return null;
        CategoryMapper mapper = org.mapstruct.factory.Mappers.getMapper(CategoryMapper.class);
        return categories.stream().map(mapper::toDto).toList();
    }

    @Named("mapSuccessCriteria")
    default List<SuccessCriteriaDto> mapSuccessCriteria(List<SuccessCriteria> criteria) {
        if (criteria == null) return null;
        SuccessCriteriaMapper mapper = org.mapstruct.factory.Mappers.getMapper(SuccessCriteriaMapper.class);
        return criteria.stream().map(mapper::toDto).toList();
    }
}
