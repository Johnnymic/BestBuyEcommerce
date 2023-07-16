package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.repository.BrandRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.domain.repository.SubCategoryRepository;
import com.bestbuy.ecommerce.dto.request.AddNewProductRequest;

import com.bestbuy.ecommerce.dto.request.UpdateProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.exceptions.AlreadyExistException;
import com.bestbuy.ecommerce.exceptions.BrandNotFoundException;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.AdminService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor

public class AdminServiceImp implements AdminService {

    private final ProductRepository productRepository;


    private final SubCategoryRepository subCategoryRepository;

    private final BrandRepository brandRepository;
    @Override
    public ProductResponse addNewProduct(AddNewProductRequest addNewProductRequest) {
        if(productRepository.existsByProductName(addNewProductRequest.getName())){
            throw new AlreadyExistException(addNewProductRequest.getName() + "Already exist");
        }
        SubCategory category = subCategoryRepository.findBySubCategoryName(addNewProductRequest.getSubCategory())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

        Brand brand  = brandRepository.findByBrandName(addNewProductRequest.getBrand())
                .orElseThrow(()-> new BrandNotFoundException("brand not found"));

        Product product = mapToProduct(addNewProductRequest,category,brand);
        var newProduct =  productRepository.save(product);
        return mapToResponse(newProduct,category,brand) ;
    }

    @Override
    public ProductResponse fetchSingleProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("product not found"));

        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long productId, UpdateProductRequest updateProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        SubCategory category = subCategoryRepository.findBySubCategoryName(updateProduct.getSubCategory())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

        Brand brand  = brandRepository.findByBrandName(updateProduct.getBrand())
                .orElseThrow(()-> new BrandNotFoundException("brand not found"));

        product.setProductName(updateProduct.getName());
        product.setBrand(brand);
        product.setPrice(updateProduct.getPrice());
        product.setCategory(category);
        product.setQuantityAvailable(updateProduct.getAvailableQty());
        product.setImageUrl(updateProduct.getImageUrl());
        product.setDescription(updateProduct.getDescription());
        productRepository.save(product);
        return mapToResponse(product,category,brand);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
        return "product is deleted successfully";
    }


    private ProductResponse mapToProductResponse(Product newProduct) {
        return ProductResponse.builder()
                .productName(newProduct.getProductName())
                .description(newProduct.getDescription())
                .price(newProduct.getPrice())
                .quantityAvailable(newProduct.getQuantityAvailable())
                .createdAt(Date.from(Instant.now()))
                .updateAt(Date.from(Instant.now()))
                .build();
    }


    private ProductResponse mapToResponse(Product newProduct,SubCategory category, Brand brand) {
        return ProductResponse.builder()
                .subCategoryName(SubCategory.builder()
                        .subCategoryName(category.getSubCategoryName())
                        .subCategoryId(category.getSubCategoryId())
                        .category(category.getCategory())
                        .build())
                .brandName(Brand.builder()
                        .brandName(brand.getBrandName())
                        .brandDescription(brand.getBrandDescription())
                        .build())
                .productName(newProduct.getProductName())
                .description(newProduct.getDescription())
                .price(newProduct.getPrice())
                .quantityAvailable(newProduct.getQuantityAvailable())
                .createdAt(Date.from(Instant.now()))
                .updateAt(Date.from(Instant.now()))
                .build();

    }


    private Product mapToProduct(AddNewProductRequest addNewProductRequest, SubCategory subCategory, Brand brand) {
      return    Product.builder()
                  .productName(addNewProductRequest.getName())
                  .price(addNewProductRequest.getPrice())
                  .quantityAvailable(addNewProductRequest.getAvailableQty())
                  .category(subCategory)
                  .brand(brand)
                  .imageUrl(addNewProductRequest.getImageUrl())
                  .description(addNewProductRequest.getDescription())
                  .build();

    }
}
