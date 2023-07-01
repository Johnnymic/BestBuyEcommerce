package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse addNewProduct(Long brandId,Long categoryId,ProductRequest productRequest);

    List<ProductResponse> findAllProductByCategory(Long categoryId);

    ProductResponse getProductByCategoryId(Long productId, Long categoryId);


    ProductResponse updateProductByCategory(Long productId, Long categoryId, ProductRequest productRequest);

    String  deleteProduct(Long productId, Long categoryId);
}
