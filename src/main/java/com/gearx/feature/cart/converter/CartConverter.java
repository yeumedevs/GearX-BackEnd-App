package com.gearx.feature.cart.converter;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.gearx.feature.cart.dto.response.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CartConverter {

    default CartPageResponse toPageResponse(
            List<CartItemResponse> items, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / (double) size);
        return CartPageResponse.builder()
                .items(items)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    @Mapping(target = "item", source = "item")
    @Mapping(target = "totals", source = "totals")
    AddItemResponse toAddItemResponse(CartItemResponse item, CartTotalsResponse totals);

    @Mapping(target = "item", source = "item")
    @Mapping(target = "removed", source = "removed")
    @Mapping(target = "removedItemId", source = "removedItemId")
    @Mapping(target = "totals", source = "totals")
    UpdateItemQtyResponse toUpdateItemQtyResponse(
            CartItemResponse item,
            Boolean removed,
            Integer removedItemId,
            CartTotalsResponse totals);

    @Mapping(target = "removedItemId", source = "removedItemId")
    @Mapping(target = "totals", source = "totals")
    RemoveItemResponse toRemoveItemResponse(Integer removedItemId, CartTotalsResponse totals);

    ClearCartResponse toClearCartResponse(CartTotalsResponse totals);
}
