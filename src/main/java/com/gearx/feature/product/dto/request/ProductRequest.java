package com.gearx.feature.product.dto.request;

import java.math.BigDecimal;

import com.gearx.common.base.model.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest extends BaseEntity {

    String name;
    String shortDescription;
    String description;
    Integer brandId;
    Integer categoryId;
    String model;
    // jsonb
    String attributes;
    BigDecimal price;
    BigDecimal compareAtPrice;
    String currencyCode; // character(10)
    Integer stockQuantity;
    Integer warrantyMonths; // smallint
    String mainImageUrl;
    String imageUrls;
}
