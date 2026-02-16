package com.gearx.feature.cart.dto.response;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartItemResponse {

    Integer id;
    Integer productId;
    String name;
    String mainImageUrl;
    BigDecimal price;
    Integer quantity;
    BigDecimal subTotal;
}
