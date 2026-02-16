package com.gearx.feature.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gearx.common.response.PageResponse;
import com.gearx.feature.product.dto.request.ProductRequest;
import com.gearx.feature.product.dto.response.ProductResponse;

public interface ProductService {

    int create(ProductRequest req);

    int update(Integer id, ProductRequest req);

    String uploadMainImage(Long productId, MultipartFile file, Integer userId);

    List<String> uploadGallery(Long productId, List<MultipartFile> files, Integer userId);

    ProductResponse findById(Integer id);

    PageResponse<ProductResponse> pageSearch(
            String q,
            Integer brandId,
            Integer categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean active,
            Boolean hasStock,
            String attributesContains,
            int page,
            int size,
            String sortBy,
            String dir);

    PageResponse<ProductResponse> pageByBrandId(
            Integer brandId,
            String q,
            Boolean active,
            Boolean hasStock,
            int page,
            int size,
            String sortBy,
            String dir);

    PageResponse<ProductResponse> pageByCategoryId(
            Integer categoryId,
            String q,
            Boolean active,
            Boolean hasStock,
            int page,
            int size,
            String sortBy,
            String dir);

    int softDelete(Integer id, String updatedBy);

    int restore(Integer id, String updatedBy);

    int updateActive(Integer id, short isActive, String updatedBy);

    int insert(ProductRequest req);

    PageResponse<ProductResponse> search(
            String q,
            Integer brandId,
            Integer categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean active,
            Boolean hasStock,
            String attributesContains,
            int page,
            int size,
            String sortBy,
            String dir);
}
