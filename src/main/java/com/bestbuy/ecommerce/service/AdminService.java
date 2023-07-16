package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.AddNewProductRequest;
import com.bestbuy.ecommerce.dto.request.UpdateProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;

public interface AdminService {

    ProductResponse addNewProduct(AddNewProductRequest addNewProductRequest);

    ProductResponse fetchSingleProduct(Long productId);

    ProductResponse updateProduct(Long productId, UpdateProductRequest updateProduct);

    String deleteProduct(Long productId);
}
