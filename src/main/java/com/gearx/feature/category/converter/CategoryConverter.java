package com.gearx.feature.category.converter;

import org.mapstruct.Mapper;

import com.gearx.common.base.converter.BaseConverter;
import com.gearx.feature.category.dto.request.CategoryRequest;
import com.gearx.feature.category.dto.response.CategoryResponse;
import com.gearx.feature.category.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryConverter
        extends BaseConverter<CategoryRequest, CategoryResponse, Category> {}
