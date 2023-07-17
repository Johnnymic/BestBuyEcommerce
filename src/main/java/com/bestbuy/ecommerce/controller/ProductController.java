package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private  final ProductService productService;






    @GetMapping("/view/all/products/")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>fetchAllProduct(){
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(productService.fetchAllProducts());
            return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("/fetch/all/product/paginated")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>>fetchProductByPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                     @RequestParam(defaultValue = "16") Integer pageSize,
                                                                                     @RequestParam(defaultValue = "Id") String sortBy,
                                                                                     @RequestParam(defaultValue = "false") boolean isAscending
                                                                                     ) {
        ApiResponse<Page<ProductResponse>> apiResponse = new ApiResponse<>(productService.fetchProductByPaginatedAndSorted(pageNo,pageSize,sortBy,isAscending));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @GetMapping("/fetch/all/product/{subcategoryId}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>>fetchProductBySubcategory( @PathVariable("subcategoryId") Long subCategoryId,
                                                                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                                                                       @RequestParam(defaultValue = "16") Integer pageSize,
                                                                                       @RequestParam(defaultValue = "Id") String sortBy,
                                                                                       @RequestParam(defaultValue = "false") boolean isAscending

    ){
        ApiResponse<Page<ProductResponse>> apiResponse = new ApiResponse<>(productService.fetchProductsBySubCategory(subCategoryId,pageNo,pageSize,sortBy,isAscending));
       return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/fetch/all/product/{brandId}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>>fetchProductByBrand( @PathVariable("brandId") Long brandId,
                                                                                  @RequestParam(defaultValue = "0") Integer pageNo,
                                                                                  @RequestParam(defaultValue = "16") Integer pageSize,
                                                                                  @RequestParam(defaultValue = "Id") String sortBy,
                                                                                  @RequestParam(defaultValue = "false") boolean isAscending

    ) {
        ApiResponse<Page<ProductResponse>> apiResponse = new ApiResponse<>(productService.fetchProductsByBrand(brandId, pageNo, pageSize, sortBy, isAscending));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view/new/arrivals/")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>viewNewArrivals(){
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>(productService.viewNewArrival());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view/best/selling")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>viewBestSelling(){
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>(productService.viewBestSelling());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }









}
