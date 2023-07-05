package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.repository.BrandRepository;
import com.bestbuy.ecommerce.domain.repository.SubCategoryRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.exceptions.BrandNotFoundException;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    @Override
    public ProductResponse addNewProduct(Long brandId,Long categoryId, ProductRequest productRequest) {
        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category id  not found"));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(()->new BrandNotFoundException("brand not found"));

        Product product = mapToEntity(productRequest);
        product.setCategory(category);
        product.setBrand(brand);

       Product newProduct =  productRepository.save(product);
        return mapToProductResponse(newProduct);
    }

    @Override
    public List<ProductResponse> findAllProductByCategory(Long categoryId) {
        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));

        List<Product> products = productRepository.findAllByCategory(category);


        return  products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductByCategoryId(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found exception"));

        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(!product.getCategory().getCategoryId().equals(category.getCategoryId())){
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse updateProductByCategory(Long productId, Long categoryId, ProductRequest productRequest) {
        Product products = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found exception"));

        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(!products.getCategory().getCategoryId().equals(category.getCategoryId())){
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        products.setProductName(productRequest.getName());

        products.setPrice(productRequest.getPrice());
        products.setDescription(productRequest.getDescription());
        products.setImageUrl(productRequest.getImageUrl());
        products.setUpdatedAt(Date.from(Instant.now()));
        products.setCreatedAt(Date.from(Instant.now()));

        productRepository.save(products);

        return mapToProductResponse(products);
    }

    @Override
    public String deleteProduct(Long productId, Long categoryId) {
        Product products = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found exception"));

        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(!products.getCategory().getCategoryId().equals(category.getCategoryId())){
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        productRepository.delete(products);
        return "product is successful deleted";
    }


    private ProductResponse mapToProductResponse(Product product) {
         return     ProductResponse.builder()
                 .brandName(product.getBrand().getBrandName())
                 .categoryName(product.getCategory().getCategoryName())
                 .productName(product.getProductName())
                 .description(product.getDescription())
                 .price(product.getPrice())
                 .quantityAvailable(5)
                 .isOutOfStock(false)
                 .createdAt(product.getCreatedAt())
                 .updateAt(product.getUpdatedAt())
                 .build();

    }

    private Product mapToEntity(ProductRequest productRequest) {
        return Product.builder()
                .productName(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantityAvailable(productRequest.getQuantityAvailable())
                .imageUrl(productRequest.getImageUrl())
                .isOutOfStock(productRequest.isOutOfStock())
                .build();
    }
}
