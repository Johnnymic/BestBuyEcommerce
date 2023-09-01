package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.dto.request.CategoryRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.CategoryResponse;
import com.bestbuy.ecommerce.search.CategorySearchDto;
import com.bestbuy.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private  final CategoryService categoryService;

    @PostMapping("/add/new/category")
    private ResponseEntity<ApiResponse <CategoryResponse>>createCategory(@RequestBody CategoryRequest categoryRequest){
    ApiResponse<CategoryResponse> apiResponse= new ApiResponse<>(categoryService.createCategory(categoryRequest));
     return    new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view/categories")
    private ResponseEntity<ApiResponse< List<CategoryResponse>>>getAllCategories(){
      ApiResponse <List<CategoryResponse>> categoryResponse = new ApiResponse<>(categoryService.getAllCategories());
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);

    }

    @GetMapping("/view/category/{Id}")
    private ResponseEntity<ApiResponse<CategoryResponse>>getCategory(@PathVariable("Id") Long categoryId){
     ApiResponse<CategoryResponse> categoryResponse =new ApiResponse<>( categoryService.getCategory(categoryId));
         return new  ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PutMapping("/update/category/{Id}")
    private ResponseEntity<ApiResponse <CategoryResponse>>editCategory(@PathVariable("Id")Long categoryId,
                                                                       @RequestBody CategoryRequest categoryRequest){
       ApiResponse <CategoryResponse> categoryResponse = new ApiResponse<>(categoryService.editCategory(categoryId,categoryRequest));
        return  new ResponseEntity<>(categoryResponse,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/category/{Id}")
    private ResponseEntity<ApiResponse<String>>deleteCategory(@PathVariable("Id") Long categoryId){
        ApiResponse<String> apiResponse = new ApiResponse<>(categoryService.deleteCategoryById(categoryId));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }

    @GetMapping("/search/category")
    private ResponseEntity<ApiResponse <Page<Category>>>filterAndSearchCategory(
        @RequestParam (defaultValue = "0")  int pageNo,
        @RequestParam(defaultValue = "10")  int pageSize,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam String firstName


    ){
        ApiResponse<Page<Category>> categorySearchApi = new ApiResponse<>(categoryService.filterAndSearchCategory(pageNo,pageSize,sortBy, firstName));
        return new ResponseEntity<>(categorySearchApi, HttpStatus.OK);

    }



}
