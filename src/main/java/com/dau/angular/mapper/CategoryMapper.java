package com.dau.angular.mapper;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO entityToDto(Category category);
    Category dtoToEntity(CategoryDTO categoryDTO);

    @Mapping(target = "id", ignore = true) // Không cập nhật ID
    void updateFromDto(CategoryDTO dto, @MappingTarget Category category);
}
