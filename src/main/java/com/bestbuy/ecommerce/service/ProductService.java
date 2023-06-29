package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;

public interface ProductService {
    ProductResponse addNewProduct(ProductRequest productRequest);
}
