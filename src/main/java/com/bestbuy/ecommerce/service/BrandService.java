package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.dto.request.BrandRequest;
import com.bestbuy.ecommerce.dto.request.BrandSearchRequest;
import com.bestbuy.ecommerce.dto.responses.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse  createBrand(BrandRequest brandRequest);

   List<BrandResponse> getAllBrands();

    BrandResponse  getBrandById(Long brandId);

    BrandResponse updateBrand(Long brandId, BrandRequest brandRequest);

    String deleteBrand(Long brandId);

    List<Brand> searchBrandByName(BrandSearchRequest brandSearchRequest);
}
