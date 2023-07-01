package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.repository.CategoryRepository;
import com.bestbuy.ecommerce.dto.request.CategoryRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.CategoryResponse;
import com.bestbuy.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CategoryController {

    private  final CategoryService categoryService;

    @PostMapping("add-category")
    private ResponseEntity<ApiResponse <CategoryResponse>>createCategory(@RequestBody CategoryRequest categoryRequest){
    ApiResponse<CategoryResponse> apiResponse= new ApiResponse<>(categoryService.createCategory(categoryRequest));
     return    new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("list-of-category")
    private ResponseEntity<ApiResponse< List<CategoryResponse>>>getAllCategories(){
      ApiResponse <List< CategoryResponse>> categoryResponse = new ApiResponse<>(categoryService.getAllCategories());
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);

    }

    @GetMapping("category/{Id}")
    private ResponseEntity<ApiResponse< CategoryResponse>>getCategory(@PathVariable("Id") Long categoryId){
     ApiResponse<CategoryResponse> categoryResponse =new ApiResponse<>( categoryService.getCategory(categoryId));
         return new  ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PutMapping("update-category/{Id}")
    private ResponseEntity<ApiResponse <CategoryResponse>>editCategory(@PathVariable("Id")Long categoryId,
                                                         @RequestBody CategoryRequest categoryRequest){
       ApiResponse <CategoryResponse> categoryResponse = new ApiResponse<>(categoryService.editCategory(categoryId,categoryRequest));
        return  new ResponseEntity<>(categoryResponse,HttpStatus.FOUND);
    }

    @DeleteMapping("{Id}")
    private ResponseEntity<ApiResponse<String>>deleteCategory(@PathVariable("Id") Long categoryId){
        ApiResponse<String> apiResponse = new ApiResponse<>(categoryService.deleteCategoryById(categoryId));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }

}
