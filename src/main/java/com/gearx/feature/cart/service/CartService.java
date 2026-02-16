package com.gearx.feature.cart.service;

import com.gearx.feature.cart.dto.request.*;
import com.gearx.feature.cart.dto.response.*;

public interface CartService {
    CartPageResponse getMyCart(Integer userId, CartPageQueryRequest query);

    AddItemResponse addItem(Integer userId, AddItemRequest req);

    UpdateItemQtyResponse updateItemQty(Integer userId, UpdateItemQtyRequest req);

    RemoveItemResponse removeItem(Integer userId, Integer itemId);

    ClearCartResponse clearMyCart(Integer userId);

    CartTotalsResponse totals(Integer userId);
}
