package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.SubCategoryRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.SubCategoryResponse;
import com.bestbuy.ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CategoryController {

    private  final SubCategoryService categoryService;

    @PostMapping("add-category")
    private ResponseEntity<ApiResponse <SubCategoryResponse>>createCategory(@RequestBody SubCategoryRequest categoryRequest){
    ApiResponse<SubCategoryResponse> apiResponse= new ApiResponse<>(categoryService.createCategory(categoryRequest));
     return    new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("list-of-category")
    private ResponseEntity<ApiResponse< List<SubCategoryResponse>>>getAllCategories(){
      ApiResponse <List<SubCategoryResponse>> categoryResponse = new ApiResponse<>(categoryService.getAllCategories());
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);

    }

    @GetMapping("category/{Id}")
    private ResponseEntity<ApiResponse<SubCategoryResponse>>getCategory(@PathVariable("Id") Long categoryId){
     ApiResponse<SubCategoryResponse> categoryResponse =new ApiResponse<>( categoryService.getCategory(categoryId));
         return new  ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PutMapping("update-category/{Id}")
    private ResponseEntity<ApiResponse <SubCategoryResponse>>editCategory(@PathVariable("Id")Long categoryId,
                                                                          @RequestBody SubCategoryRequest categoryRequest){
       ApiResponse <SubCategoryResponse> categoryResponse = new ApiResponse<>(categoryService.editCategory(categoryId,categoryRequest));
        return  new ResponseEntity<>(categoryResponse,HttpStatus.FOUND);
    }

    @DeleteMapping("{Id}")
    private ResponseEntity<ApiResponse<String>>deleteCategory(@PathVariable("Id") Long categoryId){
        ApiResponse<String> apiResponse = new ApiResponse<>(categoryService.deleteCategoryById(categoryId));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }

}
