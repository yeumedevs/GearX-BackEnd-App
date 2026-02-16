package com.gearx.feature.brand.dto.response;

import com.gearx.common.base.model.BaseEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse extends BaseEntity {

    Integer brandId;
    String name;
    String description;
}
