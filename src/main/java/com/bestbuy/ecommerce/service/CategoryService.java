package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.dto.request.CategoryRequest;
import com.bestbuy.ecommerce.dto.responses.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);



    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategory(Long categoryId);

    CategoryResponse editCategory(Long categoryId, CategoryRequest categoryRequest);

    String  deleteCategoryById(Long categoryId);

    Page<Category> filterAndSearchCategory(int pageNo, int pageSize, String sortBy, String firstName);
}
