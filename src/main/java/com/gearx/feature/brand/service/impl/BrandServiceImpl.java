package com.gearx.feature.brand.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gearx.common.exception.AppException;
import com.gearx.common.exception.ErrorCode;
import com.gearx.common.response.PageResponse;
import com.gearx.feature.brand.converter.BrandConverter;
import com.gearx.feature.brand.dto.request.BrandRequest;
import com.gearx.feature.brand.dto.response.BrandResponse;
import com.gearx.feature.brand.entity.Brand;
import com.gearx.feature.brand.mapper.BrandMapper;
import com.gearx.feature.brand.service.BrandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;
    private final BrandConverter brandConverter;

    @Override
    public int create(BrandRequest req) {
        if (brandMapper.existsByName(req.getName())) {
            throw new AppException(ErrorCode.ALREADY_EXIST);
        }
        Brand e = brandConverter.toEntity(req);
        if (e.getIsActive() == null) e.setIsActive(1);
        if (e.getIsDeleted() == null) e.setIsDeleted(0);
        return brandMapper.insert(e);
    }

    @Override
    public int update(Integer id, BrandRequest req) {
        Brand existed = brandMapper.findById(id);
        if (existed == null) throw new AppException(ErrorCode.NOT_FOUND);
        brandConverter.updateEntity(req, existed); // IGNORE nulls
        existed.setBrandId(id);
        return brandMapper.updateById(existed);
    }

    @Override
    public BrandResponse findById(Integer id) {
        Brand e = brandMapper.findById(id);
        if (e == null) throw new AppException(ErrorCode.NOT_FOUND);
        return brandConverter.toResponse(e);
    }

    @Override
    public PageResponse<BrandResponse> pageSearch(
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

        List<Brand> rows = brandMapper.pageSearch(params);
        int total = (int) brandMapper.countPageSearch(params);

        return brandConverter.toResponsePage(rows, offset, limit, total);
    }

    @Override
    public int softDelete(Integer id, String updatedBy) {
        return brandMapper.softDeleteById(id, updatedBy);
    }

    @Override
    public int restore(Integer id, String updatedBy) {
        return brandMapper.restoreById(id, updatedBy);
    }

    @Override
    public int updateActive(Integer id, short isActive, String updatedBy) {
        return brandMapper.updateActive(id, isActive, updatedBy);
    }

    @Override
    public int insert(BrandRequest req) {
        return create(req);
    }

    @Override
    public PageResponse<BrandResponse> search(
            String q, Boolean active, int page, int size, String sortBy, String dir) {
        return pageSearch(q, active, page, size, sortBy, dir);
    }
}
