package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.dto.request.SubCategoryRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.SubCategoryResponse;
import com.bestbuy.ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subCategory")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping("/admin/new/{catId}")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> addNewSubCategory(@RequestBody SubCategoryRequest subCategoryRequest,
                                                                              @PathVariable("catId") Long categoryId){
        ApiResponse<SubCategoryResponse> apiResponse = new ApiResponse<>(subCategoryService.addNewCategory(subCategoryRequest,categoryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping("/view-all-subCategory/")
    public ResponseEntity<ApiResponse <List<SubCategoryResponse>>> viewAllSubCategory(){
        ApiResponse <List<SubCategoryResponse>> apiResponse = new ApiResponse<>(subCategoryService.viewAllCategories());
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/admin/edit-subCategory/{sub_catId}")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> editSubCategory(@RequestBody SubCategoryRequest subCategoryRequest,
                                                                            @PathVariable("sub_catId") Long subCategoryId){
        ApiResponse <SubCategoryResponse> apiResponse = new ApiResponse<>(subCategoryService.editSubCategory(subCategoryRequest,subCategoryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete-subCategory/{sub_catId}")
    public ResponseEntity<ApiResponse<String>> deleteSubCategory(@PathVariable("sub_catId") Long subCategoryId){
        ApiResponse <String> apiResponse = new ApiResponse<>(subCategoryService.deleteSubCategory(subCategoryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view-subCategory/{sub_catId}")
    public ResponseEntity<ApiResponse <Set<SubCategoryResponse>>>viewSubCategoryByCategory(@PathVariable("sub_catId") Long categoryId){
        ApiResponse<Set<SubCategoryResponse>> apiResponse = new ApiResponse<>(subCategoryService.viewSubCategoryByCategory(categoryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


}
