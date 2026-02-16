package com.gearx.feature.brand.dto.request;

import com.gearx.common.base.model.BaseEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest extends BaseEntity {

    String name;
    String description;
}
