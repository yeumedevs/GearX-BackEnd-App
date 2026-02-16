package com.gearx.feature.brand.converter;

import org.mapstruct.Mapper;

import com.gearx.common.base.converter.BaseConverter;
import com.gearx.feature.brand.dto.request.BrandRequest;
import com.gearx.feature.brand.dto.response.BrandResponse;
import com.gearx.feature.brand.entity.Brand;

@Mapper(componentModel = "spring")
public interface BrandConverter extends BaseConverter<BrandRequest, BrandResponse, Brand> {}
