package com.gearx.feature.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gearx.common.exception.AppException;
import com.gearx.common.exception.ErrorCode;
import com.gearx.common.response.PageResponse;
import com.gearx.feature.product.converter.ProductConverter;
import com.gearx.feature.product.dto.request.ProductRequest;
import com.gearx.feature.product.dto.response.ProductResponse;
import com.gearx.feature.product.entity.Product;
import com.gearx.feature.product.mapper.ProductMapper;
import com.gearx.feature.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductConverter productConverter;

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder}")
    private String folder;

    @Override
    public int create(ProductRequest req) {
        if (Boolean.TRUE.equals(productMapper.existsByName(req.getName()))) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        Product e = productConverter.toEntity(req);
        if (e.getIsActive() == null) e.setIsActive(1);
        if (e.getIsDeleted() == null) e.setIsDeleted(0);
        return productMapper.insert(e);
    }

    @Override
    public int update(Integer id, ProductRequest req) {
        Product existed = productMapper.findById(id);
        if (existed == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        productConverter.updateEntity(req, existed);
        existed.setProductId(id);
        return productMapper.updateById(existed);
    }

    @Override
    public String uploadMainImage(Long productId, MultipartFile file, Integer userId) {
        String url = uploadOne(file);
        Map<String, Object> p = new HashMap<>();
        p.put("productId", productId);
        p.put("mainImageUrl", url);
        p.put("updatedBy", userId);
        productMapper.updateMainImage(productId, url, userId);
        return url;
    }

    @Override
    public List<String> uploadGallery(Long productId, List<MultipartFile> files, Integer userId) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile f : files) {
            if (f != null && !f.isEmpty()) urls.add(uploadOne(f));
        }
        Map<String, Object> p = new HashMap<>();
        p.put("productId", productId);
        p.put("imageUrls", urls);
        p.put("updatedBy", userId);
        productMapper.updateGallery(productId, urls, userId);
        return urls;
    }

    @Override
    public ProductResponse findById(Integer id) {
        Product e = productMapper.findById(id);
        if (e == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        return productConverter.toResponse(e);
    }

    @Override
    public PageResponse<ProductResponse> pageSearch(
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
            String dir) {

        int limit = Math.max(1, size);
        int offset = Math.max(0, (Math.max(1, page) - 1) * limit);

        Map<String, Object> params = new HashMap<>();
        params.put("q", q);
        params.put("brandId", brandId);
        params.put("categoryId", categoryId);
        params.put("minPrice", minPrice);
        params.put("maxPrice", maxPrice);
        params.put("active", active);
        params.put("hasStock", hasStock);
        params.put("attributesContains", attributesContains);
        params.put("sortBy", sortBy);
        params.put("dir", dir);
        params.put("offset", offset);
        params.put("limit", limit);

        List<Product> rows = productMapper.pageSearch(params);
        int total = (int) productMapper.countPageSearch(params);

        return productConverter.toResponsePage(rows, offset, limit, total);
    }

    @Override
    public PageResponse<ProductResponse> pageByBrandId(
            Integer brandId,
            String q,
            Boolean active,
            Boolean hasStock,
            int page,
            int size,
            String sortBy,
            String dir) {

        int limit = Math.max(1, size);
        int offset = Math.max(0, (Math.max(1, page) - 1) * limit);

        Map<String, Object> params = new HashMap<>();
        params.put("brandId", brandId);
        params.put("q", q);
        params.put("active", active);
        params.put("hasStock", hasStock);
        params.put("sortBy", sortBy);
        params.put("dir", dir);
        params.put("offset", offset);
        params.put("limit", limit);

        List<Product> rows = productMapper.pageByBrandId(params);
        int total = (int) productMapper.countPageByBrandId(params);

        return productConverter.toResponsePage(rows, offset, limit, total);
    }

    @Override
    public PageResponse<ProductResponse> pageByCategoryId(
            Integer categoryId,
            String q,
            Boolean active,
            Boolean hasStock,
            int page,
            int size,
            String sortBy,
            String dir) {

        int limit = Math.max(1, size);
        int offset = Math.max(0, (Math.max(1, page) - 1) * limit);

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("q", q);
        params.put("active", active);
        params.put("hasStock", hasStock);
        params.put("sortBy", sortBy);
        params.put("dir", dir);
        params.put("offset", offset);
        params.put("limit", limit);

        List<Product> rows = productMapper.pageByCategoryId(params);
        int total = (int) productMapper.countPageByCategoryId(params);

        return productConverter.toResponsePage(rows, offset, limit, total);
    }

    @Override
    public int softDelete(Integer id, String updatedBy) {
        return productMapper.softDeleteById(id, updatedBy);
    }

    @Override
    public int restore(Integer id, String updatedBy) {
        return productMapper.restoreById(id, updatedBy);
    }

    @Override
    public int updateActive(Integer id, short isActive, String updatedBy) {
        return productMapper.updateActive(id, isActive, updatedBy);
    }

    @Override
    public int insert(ProductRequest req) {
        return create(req);
    }

    @Override
    public PageResponse<ProductResponse> search(
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
            String dir) {
        return pageSearch(
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
                dir);
    }

    private String uploadOne(MultipartFile file) {
        try {
            Map<?, ?> res =
                    cloudinary
                            .uploader()
                            .upload(file.getBytes(), ObjectUtils.asMap("folder", folder));
            return (String) res.get("secure_url"); // URL https
        } catch (Exception e) {
            throw new RuntimeException("Upload to Cloudinary failed", e);
        }
    }
}
