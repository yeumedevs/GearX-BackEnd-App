package com.gearx.feature.category.dto.request;

import com.gearx.common.base.model.BaseEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest extends BaseEntity {

    String name;
    String description;
}
