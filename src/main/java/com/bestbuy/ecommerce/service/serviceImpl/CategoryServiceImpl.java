package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.domain.repository.CategoryRepository;
import com.bestbuy.ecommerce.dto.request.CategoryRequest;
import com.bestbuy.ecommerce.dto.responses.CategoryResponse;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryName(category.getCategoryName())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
            return     category
                .stream()
                .map(this::maoToCategory)
                .collect(Collectors.toList());

    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        return  maoToCategory(category);
    }

    @Override
    public CategoryResponse editCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        category.setCategoryName(categoryRequest.getCategoryName());
       Category newCategory = categoryRepository.save(category);
        return maoToCategory(newCategory);
    }

    @Override
    public String deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        categoryRepository.delete(category);
        return  category + " is deleted";
    }


    private CategoryResponse maoToCategory(Category category){
        return CategoryResponse.builder()
                .categoryName(category.getCategoryName())
                .build();
    }




}