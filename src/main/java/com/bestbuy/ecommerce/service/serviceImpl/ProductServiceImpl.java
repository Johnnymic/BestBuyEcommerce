package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.repository.CategoryRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.exceptions.ProductNotFoundException;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse addNewProduct(ProductRequest productRequest ) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category id  not found"));
        Product product = mapToEntity(productRequest);
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdateAt(LocalDateTime.now());
       Product newProduct =  productRepository.save(product);
        return mapToProductResponse(newProduct);
    }

    @Override
    public List<ProductResponse> findAllProductByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));

        List<Product> products = productRepository.findAllByCategory(category);


        return  products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductByCategoryId(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found exception"));

        Category category = categoryRepository.findById(categoryId)
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

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(!products.getCategory().getCategoryId().equals(category.getCategoryId())){
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        products.setName(productRequest.getName());

        products.setPrice(productRequest.getPrice());
        products.setDescription(productRequest.getDescription());
        products.setImageUrl(productRequest.getImageUrl());
        products.setUpdateAt(LocalDateTime.now());
        products.setCreatedAt(LocalDateTime.now());

        productRepository.save(products);

        return mapToProductResponse(products);
    }

    @Override
    public String deleteProduct(Long productId, Long categoryId) {
        Product products = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found exception"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(!products.getCategory().getCategoryId().equals(category.getCategoryId())){
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        productRepository.delete(products);
        return "product is successful deleted";
    }


    private ProductResponse mapToProductResponse(Product product) {
         return     ProductResponse.builder()
                 .name(product.getName())
                 .description(product.getDescription())
                 .categoryName(product.getCategory().getCategoryName())
                 .createdAt(product.getCreatedAt())
                 .updateAt(product.getUpdateAt())
                 .build();

    }

    private Product mapToEntity(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }
}
