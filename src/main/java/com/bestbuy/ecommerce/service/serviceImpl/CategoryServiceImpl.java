package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.repository.CategoryRepository;
import com.bestbuy.ecommerce.domain.repository.SubCategoryRepository;
import com.bestbuy.ecommerce.dto.request.CategoryRequest;
import com.bestbuy.ecommerce.dto.responses.CategoryResponse;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.search.CategorySearch;
import com.bestbuy.ecommerce.search.CategorySearchDto;
import com.bestbuy.ecommerce.search.CategorySearchPage;
import com.bestbuy.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private  final CategorySearchDto categorySearchDto;
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = mapToResponse(categoryRequest);
        categoryRepository.save(category);
        SimpleDateFormat createdAt = new SimpleDateFormat("yyyy-MM-dd");
        return CategoryResponse.builder()
                .categoryName(category.getName())
                .createAt(createdAt.format(categoryRequest.getTime().toString()))
                .build();
    }

    private Category mapToResponse(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getCategoryName())
                .imageUrl(categoryRequest.getImageUrl())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
            return     category
                .stream()
                .map(this::mapToCategory)
                .collect(Collectors.toList());

    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        return  mapToCategory(category);
    }

    @Override
    public CategoryResponse editCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        category.setName(categoryRequest.getCategoryName());
        category.setUpdatedAt(category.getUpdatedAt());
       categoryRepository.save(category);
        return mapToCategory(category);
    }

    @Override
    public String deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        categoryRepository.delete(category);
        return "category is deleted";
    }

    @Override
    public Page<Category> filterAndSearchCategory(int pageNo, int pageSize, String sortBy, String firstName) {
      CategorySearchPage categorySearchPage = new CategorySearchPage();
      categorySearchPage.setPageNo(pageNo);
      categorySearchPage.setPageSize(pageSize);
      categorySearchPage.setSortDirection(Sort.Direction.ASC);
      categorySearchPage.setSortByName(sortBy);
        CategorySearch categorySearch  = new CategorySearch();
        categorySearch.setCategoryName(firstName);
        return categorySearchDto.searchCategoryCriteria(categorySearchPage,categorySearch);
    }


    private  CategoryResponse mapToCategory(Category category){
        SimpleDateFormat createdAt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat updateAt = new SimpleDateFormat("yyyy-MM-dd");

        return CategoryResponse.builder()
                .categoryName(category.getName())
                .createAt(createdAt.format(category.getCreatedAt()))
                .updateAt(updateAt.format(category.getUpdatedAt()))
                .build();
    }




}
