package com.gearx.feature.product.controller;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.gearx.common.constants.ApiConstants;
import com.gearx.common.response.ApiResponse;
import com.gearx.common.response.PageResponse;
import com.gearx.common.response.ResponseHandler;
import com.gearx.feature.product.dto.request.ProductRequest;
import com.gearx.feature.product.dto.response.ProductResponse;
import com.gearx.feature.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.Product.BASE)
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping(ApiConstants.Product.INSERT)
    public ResponseEntity<ApiResponse<Object>> insert(@Valid @RequestBody ProductRequest req) {
        return ResponseHandler.success("CREATED", productService.insert(req));
    }

    @PutMapping(ApiConstants.Product.UPDATE)
    public ResponseEntity<ApiResponse<Object>> update(
            @PathVariable Integer id, @Valid @RequestBody ProductRequest req) {
        return ResponseHandler.success("UPDATED", productService.update(id, req));
    }

    @PostMapping(
            path = ApiConstants.Product.UPDATE_MAIN_IMG,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadMain(
            @PathVariable("id") Long productId,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "user", required = false) Integer userId) {
        String url = productService.uploadMainImage(productId, file, userId);
        return java.util.Map.of("mainImageUrl", url);
    }

    @PostMapping(
            path = ApiConstants.Product.UPDATE_GALLERY,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadGallery(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader(value = "user", required = false) Integer userId) {
        List<String> urls = productService.uploadGallery(productId, files, userId);
        return java.util.Map.of("imageUrls", urls);
    }

    @GetMapping(ApiConstants.Product.GET_BY_ID)
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Integer id) {
        return ResponseHandler.success(productService.findById(id));
    }

    @GetMapping(ApiConstants.Product.SEARCH_PAGEABLE)
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean hasStock,
            @RequestParam(required = false) String attributesContains,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String dir) {
        return ResponseHandler.success(
                productService.search(
                        q,
                        brandId,
                        categoryId,
                        minPrice,
                        maxPrice,
                        active,
                        hasStock,
                        attributesContains,
                        page,
                        size,
                        sortBy,
                        dir));
    }

    // Mapper có 2 page theo brand/category
    @GetMapping(ApiConstants.Product.SEARCH_BY_BRAND_PAGE)
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> searchByBrand(
            @PathVariable Integer brandId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean hasStock,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String dir) {
        return ResponseHandler.success(
                productService.pageByBrandId(
                        brandId, q, active, hasStock, page, size, sortBy, dir));
    }

    @GetMapping(ApiConstants.Product.SEARCH_BY_CATE_PAGE)
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> searchByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean hasStock,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String dir) {
        return ResponseHandler.success(
                productService.pageByCategoryId(
                        categoryId, q, active, hasStock, page, size, sortBy, dir));
    }

    @DeleteMapping(ApiConstants.Product.SOFT_DELETE)
    public ResponseEntity<ApiResponse<Object>> softDelete(
            @PathVariable Integer id, @RequestParam String updatedBy) {
        return ResponseHandler.success("SOFT_DELETED", productService.softDelete(id, updatedBy));
    }

    @PostMapping(ApiConstants.Product.RESTORE)
    public ResponseEntity<ApiResponse<Object>> restore(
            @PathVariable Integer id, @RequestParam String updatedBy) {
        return ResponseHandler.success("RESTORED", productService.restore(id, updatedBy));
    }

    @PatchMapping(ApiConstants.Product.ACTIVE)
    public ResponseEntity<ApiResponse<Object>> updateActive(
            @PathVariable Integer id, @RequestParam short value, @RequestParam String updatedBy) {
        return ResponseHandler.success(
                "ACTIVE_UPDATED", productService.updateActive(id, value, updatedBy));
    }
}
