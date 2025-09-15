package com.example.FormService.mapper;

import com.example.FormService.dto.CategoryDto;
import com.example.FormService.model.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.example.FormService.model.Factor;
import com.example.FormService.dto.FactorDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);

    @Mapping(source = "factors", target = "factors", qualifiedByName = "mapFactors")

    @Named("mapFactors")
    default List<FactorDto> mapFactors(List<Factor> factors) {
        if (factors == null) return null;
        FactorMapper mapper = org.mapstruct.factory.Mappers.getMapper(FactorMapper.class);
        return factors.stream().map(mapper::toDto).toList();
    }
}
