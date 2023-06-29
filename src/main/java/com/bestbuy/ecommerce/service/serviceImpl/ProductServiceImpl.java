package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Category;
import com.bestbuy.ecommerce.domain.entity.Product;
import com.bestbuy.ecommerce.domain.repository.CategoryRepository;
import com.bestbuy.ecommerce.domain.repository.ProductRepository;
import com.bestbuy.ecommerce.dto.request.ProductRequest;
import com.bestbuy.ecommerce.dto.responses.ProductResponse;
import com.bestbuy.ecommerce.exceptions.CategoryNotFoundException;
import com.bestbuy.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse addNewProduct(ProductRequest productRequest ) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category id  not found"));
        Product product = mapToEntity(productRequest);
        product.setCategory(category);
       Product newProduct =  productRepository.save(product);
        return ProductResponse.builder()
                .name(newProduct.getName())
                .description(newProduct.getDescription())
                .category(newProduct.getCategory())
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
