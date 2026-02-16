package com.gearx.feature.cart.service.impl;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gearx.feature.cart.converter.CartConverter;
import com.gearx.feature.cart.dto.request.*;
import com.gearx.feature.cart.dto.response.*;
import com.gearx.feature.cart.entity.Cart;
import com.gearx.feature.cart.entity.CartItem;
import com.gearx.feature.cart.mapper.CartMapper;
import com.gearx.feature.cart.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartConverter converter;

    /** Lấy/khởi tạo cart cho user (id lấy từ auth hoặc param) */
    private Cart ensureCart(Integer userId) {

        if (Boolean.FALSE.equals(cartMapper.existsUser(userId))) {
            throw new NoSuchElementException("User not found");
        }

        Cart c = cartMapper.findCartByUserId(userId);

        if (c == null) {
            c = new Cart();
            c.setUserId(userId);

            cartMapper.createCart(c); // useGeneratedKeys
        }

        return c;
    }

    @Override
    public CartPageResponse getMyCart(Integer userId, CartPageQueryRequest query) {
        var cart = ensureCart(userId);
        int page = query.getPage() == null ? 1 : query.getPage();
        int size = query.getSize() == null ? 20 : query.getSize();

        var items =
                cartMapper.pageItems(
                        cart.getId(),
                        query.getQ(),
                        page,
                        size,
                        query.getSortBy(),
                        query.getDir(),
                        null,
                        null,
                        null);
        long total = cartMapper.countPageItems(cart.getId(), query.getQ());

        return converter.toPageResponse(items, page, size, total);
    }

    @Transactional
    @Override
    public AddItemResponse addItem(Integer userId, AddItemRequest req) {

        if (req.getQuantity() == null || req.getQuantity() < 1) {
            throw new IllegalArgumentException("quantity must be >= 1");
        }

        if (Boolean.FALSE.equals(cartMapper.existsActiveProduct(req.getProductId()))) {
            throw new NoSuchElementException("Product not found or inactive");
        }

        Integer stock = cartMapper.getProductStock(req.getProductId());

        if (stock == null || stock <= 0) throw new IllegalStateException("Product out of stock");

        Cart cart = ensureCart(userId);
        CartItem existed = cartMapper.findItemByCartAndProduct(cart.getId(), req.getProductId());

        int targetQty = req.getQuantity();
        Integer itemId;

        if (existed != null) {
            targetQty += existed.getQuantity();
            if (targetQty > stock) throw new IllegalStateException("Quantity exceeds stock");
            cartMapper.updateItemQuantity(existed.getId(), targetQty);
            itemId = existed.getId();
        } else {

            if (targetQty > stock) throw new IllegalStateException("Quantity exceeds stock");

            CartItem item = new CartItem();
            item.setCartId(cart.getId());
            item.setProductId(req.getProductId());
            item.setQuantity(req.getQuantity());

            cartMapper.insertItem(item);

            itemId = item.getId();
        }

        var itemView = cartMapper.getItemViewById(itemId);
        var totals = cartMapper.computeTotals(cart.getId());

        return converter.toAddItemResponse(itemView, totals);
    }

    @Transactional
    @Override
    public UpdateItemQtyResponse updateItemQty(Integer userId, UpdateItemQtyRequest req) {

        Cart cart = ensureCart(userId);
        CartItem found = cartMapper.findItemById(req.getItemId());

        if (found == null || !found.getCartId().equals(cart.getId())) {
            throw new NoSuchElementException("Item not found in your cart");
        }

        if (req.getQuantity() == 0) {
            cartMapper.deleteItemById(found.getId());
            var totals = cartMapper.computeTotals(cart.getId());
            return UpdateItemQtyResponse.builder()
                    .item(null)
                    .removed(true)
                    .removedItemId(found.getId())
                    .totals(totals)
                    .build();
        }

        Integer stock = cartMapper.getProductStock(found.getProductId());
        if (req.getQuantity() < 1 || req.getQuantity() > stock) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        cartMapper.updateItemQuantity(found.getId(), req.getQuantity());

        var itemView = cartMapper.getItemViewById(found.getId());
        var totals = cartMapper.computeTotals(cart.getId());

        return converter.toUpdateItemQtyResponse(itemView, false, null, totals);
    }

    @Transactional
    @Override
    public RemoveItemResponse removeItem(Integer userId, Integer itemId) {

        Cart cart = ensureCart(userId);
        CartItem found = cartMapper.findItemById(itemId);
        if (found == null || !found.getCartId().equals(cart.getId())) {
            throw new NoSuchElementException("Item not found in your cart");
        }

        cartMapper.deleteItemById(itemId);

        var totals = cartMapper.computeTotals(cart.getId());

        return converter.toRemoveItemResponse(itemId, totals);
    }

    @Override
    @Transactional
    public ClearCartResponse clearMyCart(Integer userId) {

        var cart = ensureCart(userId);

        cartMapper.clearItemsByCartId(cart.getId());

        var totals = cartMapper.computeTotals(cart.getId());

        return converter.toClearCartResponse(totals);
    }

    @Override
    public CartTotalsResponse totals(Integer userId) {
        Cart cart = ensureCart(userId);
        return cartMapper.computeTotals(cart.getId());
    }
}
