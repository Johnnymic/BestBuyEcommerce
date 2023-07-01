package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.BrandRequest;
import com.bestbuy.ecommerce.dto.responses.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse  createBrand(BrandRequest brandRequest);

   List<BrandResponse> getAllBrands();

    BrandResponse  getBrandById(Long brandId);

    BrandResponse updateBrand(Long brandId, BrandRequest brandRequest);

    String deleteBrand(Long brandId);
}
