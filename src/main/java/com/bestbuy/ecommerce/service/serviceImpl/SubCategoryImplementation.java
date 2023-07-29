package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.repository.CategoryRepository;
import com.bestbuy.ecommerce.domain.repository.SubCategoryRepository;
import com.bestbuy.ecommerce.dto.request.SubCategoryRequest;
import com.bestbuy.ecommerce.dto.responses.SubCategoryResponse;
import com.bestbuy.ecommerce.exceptions.AlreadyExistException;
import com.bestbuy.ecommerce.exceptions.CartNotFoundException;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.exceptions.SubCategoryNotFoundException;
import com.bestbuy.ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryImplementation implements SubCategoryService {

    private  final CategoryRepository categoryRepository;

    private final SubCategoryRepository subCategoryRepository;


    @Override
    public SubCategoryResponse addNewCategory(SubCategoryRequest subCategoryRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("CATEGORY NOT FOUND"));
        if (subCategoryRepository.existsBySubCategoryName(subCategoryRequest.getSubCategoryName())){
            throw new AlreadyExistException("subCategory already exist");
        }
        SubCategory subCategory = mapToSubCategory(subCategoryRequest,category);
//        subCategory.setUpdatedAt(Date.from(Instant.now()));

        var newSubCategory = subCategoryRepository.save(subCategory);
        return mapToSubCategoryResponse(newSubCategory);
    }

    @Override
    public List<SubCategoryResponse> viewAllCategories() {
        List<SubCategory> categories = subCategoryRepository.findAll();

        return categories.stream().map(this::mapToSubCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public SubCategoryResponse editSubCategory(SubCategoryRequest subCategoryRequest, Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(()->new SubCategoryNotFoundException("sub-category not found"));
        subCategory.setSubCategoryName(subCategory.getSubCategoryName());
        subCategory.setImageUrl(subCategory.getImageUrl());
//        subCategory.setUpdatedAt(Date.from(Instant.now()));
        subCategoryRepository.save(subCategory);
        return mapToSubCategoryResponse(subCategory);
    }

    @Override
    public String deleteSubCategory(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(()->new SubCategoryNotFoundException("sub-category not found"));
           subCategoryRepository.delete(subCategory);
           return "subCategory is successfully deleted ";
    }

    @Override
    public Set<SubCategoryResponse> viewSubCategoryByCategory(Long categoryId) {
         Category category = categoryRepository.findById(categoryId)
                 .orElseThrow(()-> new CartNotFoundException("category not found "));
         Set<SubCategory> subCategories = category.getSubCategories();
         Set<SubCategoryResponse> subCategoryResponses = new HashSet<>();
         subCategories.forEach( subCategory -> {
               SubCategoryResponse response = SubCategoryResponse.builder()
                      .subCategoryId(subCategory.getSubCategoryId())
                      .subCategoryName(subCategory.getSubCategoryName())
                      .imageUrl(subCategory.getImageUrl())
                      .build();
                    subCategoryResponses.add(response);
                 }
                 );
        return subCategoryResponses;
    }

    private SubCategoryResponse mapToSubCategoryResponse(SubCategory newSubCategory) {
          return    SubCategoryResponse.builder()
                     .subCategoryId(newSubCategory.getSubCategoryId())
                     .subCategoryName(newSubCategory.getSubCategoryName())
                     .imageUrl(newSubCategory.getImageUrl())
//                     .createAt(newSubCategory.getCreatedAt())
                     .build();
    }

    private SubCategory mapToSubCategory(SubCategoryRequest subCategoryRequest, Category category) {
          return      SubCategory.builder()
                  .subCategoryName(subCategoryRequest.getSubCategoryName())

                  .category(category)
                  .build();


    }


}
