package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.SubCategoryRequest;
import com.bestbuy.ecommerce.dto.responses.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    SubCategoryResponse createCategory(SubCategoryRequest categoryRequest);



    List<SubCategoryResponse> getAllCategories();

    SubCategoryResponse getCategory(Long categoryId);

    SubCategoryResponse editCategory(Long categoryId, SubCategoryRequest categoryRequest);

    String  deleteCategoryById(Long categoryId);
}
