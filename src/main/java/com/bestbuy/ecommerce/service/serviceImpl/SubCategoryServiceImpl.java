package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.repository.SubCategoryRepository;
import com.bestbuy.ecommerce.dto.request.SubCategoryRequest;
import com.bestbuy.ecommerce.dto.responses.SubCategoryResponse;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository categoryRepository;
    @Override
    public SubCategoryResponse createCategory(SubCategoryRequest categoryRequest) {
        SubCategory category = new SubCategory();
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return SubCategoryResponse.builder()
                .categoryName(category.getCategoryName())
                .build();
    }

    @Override
    public List<SubCategoryResponse> getAllCategories() {
        List<SubCategory> category = categoryRepository.findAll();
            return     category
                .stream()
                .map(this::mapToCategory)
                .collect(Collectors.toList());

    }

    @Override
    public SubCategoryResponse getCategory(Long categoryId) {
        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        return  mapToCategory(category);
    }

    @Override
    public SubCategoryResponse editCategory(Long categoryId, SubCategoryRequest categoryRequest) {
        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        category.setCategoryName(categoryRequest.getCategoryName());
       categoryRepository.save(category);
        return mapToCategory(category);
    }

    @Override
    public String deleteCategoryById(Long categoryId) {
        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        categoryRepository.delete(category);
        return "category is deleted";
    }


    private SubCategoryResponse mapToCategory(SubCategory category){
        return SubCategoryResponse.builder()
                .categoryName(category.getCategoryName())
                .build();
    }




}
