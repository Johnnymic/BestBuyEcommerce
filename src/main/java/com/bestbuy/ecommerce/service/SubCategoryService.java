package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.dto.request.SubCategoryRequest;
import com.bestbuy.ecommerce.dto.responses.SubCategoryResponse;

import java.util.List;
import java.util.Set;

public interface SubCategoryService {
    SubCategoryResponse addNewCategory(SubCategoryRequest subCategoryRequest, Long categoryId);

     List<SubCategoryResponse> viewAllCategories();

    SubCategoryResponse editSubCategory(SubCategoryRequest subCategoryRequest, Long subCategoryId);

   String  deleteSubCategory(Long subCategoryId);

   Set<SubCategoryResponse> viewSubCategoryByCategory(Long categoryId);
}
