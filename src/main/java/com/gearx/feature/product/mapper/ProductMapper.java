package com.gearx.feature.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gearx.feature.product.entity.Product;

@Mapper
public interface ProductMapper {

    int insert(Product product);

    int updateById(Product product);

    int updateMainImage(
            @Param("productId") Long productId,
            @Param("mainImageUrl") String mainImageUrl,
            @Param("updatedBy") Integer updatedBy);

    int updateGallery(
            @Param("productId") Long productId,
            @Param("imageUrls") List<String> imageUrls,
            @Param("updatedBy") Integer updatedBy);

    Product findById(@Param("productId") Integer productId);

    int softDeleteById(@Param("productId") Integer productId, @Param("updatedBy") String updatedBy);

    int restoreById(@Param("productId") Integer productId, @Param("updatedBy") String updatedBy);

    int updateActive(
            @Param("productId") Integer productId,
            @Param("isActive") Short isActive,
            @Param("updatedBy") String updatedBy);

    boolean existsByName(@Param("name") String name);

    // page + search tổng quát
    List<Product> pageSearch(Map<String, Object> params);

    long countPageSearch(Map<String, Object> params);

    // page theo brand/category
    List<Product> pageByBrandId(Map<String, Object> params);

    long countPageByBrandId(Map<String, Object> params);

    List<Product> pageByCategoryId(Map<String, Object> params);

    long countPageByCategoryId(Map<String, Object> params);
}
