package com.gearx.feature.product.converter;

import org.mapstruct.Mapper;

import com.gearx.common.base.converter.BaseConverter;
import com.gearx.feature.product.dto.request.ProductRequest;
import com.gearx.feature.product.dto.response.ProductResponse;
import com.gearx.feature.product.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductConverter extends BaseConverter<ProductRequest, ProductResponse, Product> {}
