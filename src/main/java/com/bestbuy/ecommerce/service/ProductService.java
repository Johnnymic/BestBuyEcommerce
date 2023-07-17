package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse getProductByCategoryId(Long productId, Long categoryId);

    List<ProductResponse> fetchAllProducts();

    Page<ProductResponse> fetchProductByPaginatedAndSorted(Integer pageNo, Integer pageSize, String sortBy,boolean ascending);

    Page<ProductResponse> fetchProductsBySubCategory(Long subCategoryId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);

    Page<ProductResponse> fetchProductsByBrand(Long brandId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);

    List<ProductResponse>  viewNewArrival();

    List<ProductResponse> viewBestSelling();
}
