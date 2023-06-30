package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse addNewProduct(ProductRequest productRequest);

    List<ProductResponse> findAllProductByCategory(Long categoryId);
}
