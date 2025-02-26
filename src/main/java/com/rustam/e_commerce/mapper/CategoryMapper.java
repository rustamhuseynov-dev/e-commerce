package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dto.response.ReadCategoryResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CategoryMapper {
    List<ReadCategoryResponse> toRead(List<Category> categories);

    ReadCategoryResponse toDto(Category category);
}
