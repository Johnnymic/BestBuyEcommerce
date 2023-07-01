package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.BrandRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.BrandResponse;
import com.bestbuy.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/create-brands")
    public ResponseEntity<ApiResponse<BrandResponse>>createNewBrand(@RequestBody BrandRequest brandRequest){
        ApiResponse<BrandResponse> apiResponse = new ApiResponse<>(brandService.createBrand(brandRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping ("/list-of-brands")
    public  ResponseEntity<ApiResponse<List<BrandResponse>>> listOfBrands(){
        ApiResponse<List<BrandResponse>> apiResponse = new ApiResponse<>(brandService.getAllBrands());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<ApiResponse<BrandResponse>>findBrandById(@PathVariable("brandId") Long brandId){
        ApiResponse<BrandResponse> apiResponse = new ApiResponse<>(brandService.getBrandById(brandId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{brandId}/brands")
    public ResponseEntity<ApiResponse<BrandResponse>>editBrand(@PathVariable("brandId") Long brandId ,@RequestBody BrandRequest brandRequest){
        ApiResponse<BrandResponse> apiResponse = new ApiResponse<>(brandService.updateBrand(brandId,brandRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<ApiResponse<String>>deleteBrand(@PathVariable("brandId") Long brandId){
        ApiResponse<String> apiResponse = new ApiResponse<>(brandService.deleteBrand(brandId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }



}
