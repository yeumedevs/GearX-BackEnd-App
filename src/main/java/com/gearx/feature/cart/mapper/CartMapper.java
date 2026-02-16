package com.gearx.feature.cart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gearx.feature.cart.dto.response.CartItemResponse;
import com.gearx.feature.cart.dto.response.CartTotalsResponse;
import com.gearx.feature.cart.entity.Cart;
import com.gearx.feature.cart.entity.CartItem;

@Mapper
public interface CartMapper {

    Boolean existsUser(@Param("userId") Integer userId);

    Cart findCartByUserId(@Param("userId") Integer userId);

    int createCart(Cart cart);

    CartItem findItemById(@Param("id") Integer id);

    CartItem findItemByCartAndProduct(
            @Param("cartId") Integer cartId, @Param("productId") Integer productId);

    int insertItem(CartItem item);

    int updateItemQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);

    int deleteItemById(@Param("id") Integer id);

    int clearItemsByCartId(@Param("cartId") Integer cartId);

    List<CartItemResponse> pageItems(
            @Param("cartId") Integer cartId,
            @Param("q") String q,
            @Param("page") Integer page,
            @Param("size") Integer size,
            @Param("sortBy") String sortBy,
            @Param("dir") String dir,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("pageable") Object pageable);

    long countPageItems(@Param("cartId") Integer cartId, @Param("q") String q);

    Integer getProductStock(@Param("productId") Integer productId);

    CartItemResponse getItemViewById(@Param("itemId") Integer itemId);

    Boolean existsActiveProduct(@Param("productId") Integer productId);

    CartTotalsResponse computeTotals(@Param("cartId") Integer cartId);
}
