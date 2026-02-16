package com.gearx.feature.cart.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.gearx.common.constants.ApiConstants;
import com.gearx.common.response.ApiResponse;
import com.gearx.common.response.ResponseHandler;
import com.gearx.feature.cart.dto.request.*;
import com.gearx.feature.cart.dto.response.*;
import com.gearx.feature.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.Cart.BASE)
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private Integer resolveUserId(Integer userIdFromParam, Object principal) {
        if (userIdFromParam != null) return userIdFromParam;
        return 1;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartPageResponse>> getMyCart(
            @Valid @ModelAttribute CartPageQueryRequest query,
            @RequestParam(required = false) Integer userId,
            @AuthenticationPrincipal Object principal) {

        var res = cartService.getMyCart(resolveUserId(userId, principal), query);
        return ResponseHandler.success(res);
    }

    @PostMapping(ApiConstants.Cart.ITEMS)
    public ResponseEntity<ApiResponse<AddItemResponse>> addItem(
            @Valid @RequestBody AddItemRequest req,
            @RequestParam(required = false) Integer userId,
            @AuthenticationPrincipal Object principal) {

        var res = cartService.addItem(resolveUserId(userId, principal), req);
        return ResponseHandler.success("Thêm vào giỏ hàng thành công", res);
    }

    @PutMapping(ApiConstants.Cart.UPDATE_QTY)
    public ResponseEntity<ApiResponse<UpdateItemQtyResponse>> updateQty(
            @Valid @RequestBody UpdateItemQtyRequest req,
            @RequestParam(required = false) Integer userId,
            @AuthenticationPrincipal Object principal) {

        var res = cartService.updateItemQty(resolveUserId(userId, principal), req);
        return ResponseHandler.success("Cập nhật số lượng thành công", res);
    }

    @DeleteMapping(ApiConstants.Cart.ITEMS_BY_ID)
    public ResponseEntity<ApiResponse<RemoveItemResponse>> removeItem(
            @PathVariable Integer itemId,
            @RequestParam(required = false) Integer userId,
            @AuthenticationPrincipal Object principal) {

        var res = cartService.removeItem(resolveUserId(userId, principal), itemId);
        return ResponseHandler.success("Xoá sản phẩm khỏi giỏ hàng thành công", res);
    }

    @DeleteMapping(ApiConstants.Cart.CLEAR)
    public ResponseEntity<ApiResponse<ClearCartResponse>> clear(
            @RequestParam(required = false) Integer userId,
            @AuthenticationPrincipal Object principal) {

        var res = cartService.clearMyCart(resolveUserId(userId, principal));
        return ResponseHandler.success("Đã xoá toàn bộ giỏ hàng", res);
    }

    @GetMapping(ApiConstants.Cart.TOTALS)
    public ResponseEntity<ApiResponse<CartTotalsResponse>> totals(
            @RequestParam(required = false) Integer userId,
            @AuthenticationPrincipal Object principal) {

        var res = cartService.totals(resolveUserId(userId, principal));
        return ResponseHandler.success(res);
    }
}
