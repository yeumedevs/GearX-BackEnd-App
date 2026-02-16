package com.gearx.feature.category.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gearx.feature.category.converter.CategoryConverter;
import org.springframework.stereotype.Service;

import com.gearx.common.exception.AppException;
import com.gearx.common.exception.ErrorCode;
import com.gearx.common.response.PageResponse;
import com.gearx.feature.category.converter.CategoryConverter;
import com.gearx.feature.category.dto.request.CategoryRequest;
import com.gearx.feature.category.dto.response.CategoryResponse;
import com.gearx.feature.category.entity.Category;
import com.gearx.feature.category.mapper.CategoryMapper;
import com.gearx.feature.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryConverter categoryConverter;

    @Override
    public int create(CategoryRequest req) {
        if (Boolean.TRUE.equals(categoryMapper.existsByName(req.getName()))) {
            throw new AppException(ErrorCode.ALREADY_EXIST);
        }
        Category e = categoryConverter.toEntity(req);
        if (e.getIsActive() == null) e.setIsActive(1);
        if (e.getIsDeleted() == null) e.setIsDeleted(0);
        return categoryMapper.insert(e);
    }

    @Override
    public int update(Integer id, CategoryRequest req) {
        Category existed = categoryMapper.findById(id);
        if (existed == null) throw new AppException(ErrorCode.NOT_FOUND);
        Category e = categoryConverter.toEntity(req);
        e.setCategoryId(id);
        return categoryMapper.updateById(e);
    }

    @Override
    public CategoryResponse findById(Integer id) {
        Category e = categoryMapper.findById(id);
        if (e == null) throw new AppException(ErrorCode.NOT_FOUND);
        return categoryConverter.toResponse(e);
    }

    @Override
    public PageResponse<CategoryResponse> pageSearch(
            String q, Boolean active, int page, int size, String sortBy, String dir) {

        int limit = Math.max(1, size);
        int offset = Math.max(0, (Math.max(1, page) - 1) * limit);

        Map<String, Object> params = new HashMap<>();
        params.put("q", q);
        params.put("active", active);
        params.put("sortBy", sortBy);
        params.put("dir", dir);
        params.put("offset", offset);
        params.put("limit", limit);

        List<Category> rows = categoryMapper.pageSearch(params);
        int total = (int) categoryMapper.countPageSearch(params);

        return categoryConverter.toResponsePage(rows, offset, limit, total);
    }

    @Override
    public int softDelete(Integer id, String updatedBy) {
        return categoryMapper.softDeleteById(id, updatedBy);
    }

    @Override
    public int restore(Integer id, String updatedBy) {
        return categoryMapper.restoreById(id, updatedBy);
    }

    @Override
    public int updateActive(Integer id, short isActive, String updatedBy) {
        return categoryMapper.updateActive(id, isActive, updatedBy);
    }

    @Override
    public int insert(CategoryRequest req) {
        return create(req);
    }

    @Override
    public PageResponse<CategoryResponse> search(
            String q, Boolean active, int page, int size, String sortBy, String dir) {
        return pageSearch(q, active, page, size, sortBy, dir);
    }
}
