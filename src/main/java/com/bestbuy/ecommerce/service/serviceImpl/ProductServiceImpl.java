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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository categoryRepository;

    private final BrandRepository brandRepository;



    @Override
    public ProductResponse getProductByCategoryId(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found exception"));

        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(!product.getCategory().getSubCategoryId().equals(category.getSubCategoryId())){
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        return mapToProductResponse(product);
    }

    @Override
    public List<ProductResponse> fetchAllProducts() {
        List<Product> fetchAllProduct = productRepository.findAll();
        List<ProductResponse> productResponses = fetchAllProduct.stream()
                .map(product ->  ProductResponse.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .description(product.getDescription())
                        .quantityAvailable(product.getQuantityAvailable())
                        .price(product.getPrice())
                        .brandName(product.getBrand())
                        .subCategoryName(product.getCategory())
                        .build()
                ).collect(Collectors.toList());

        return productResponses;
    }

    @Override
    public Page<ProductResponse> fetchProductByPaginatedAndSorted(Integer pageNo, Integer pageSize, String sortBy,boolean ascending) {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse>responseList = new ArrayList<>();
         productList.forEach(product ->
                  responseList.add(
                          ProductResponse.builder()
                                  .id(product.getId())
                                  .productName(product.getProductName())
                                  .description(product.getDescription())
                                  .quantityAvailable(product.getQuantityAvailable())
                                  .price(product.getPrice())
                                  .brandName(product.getBrand())
                                  .subCategoryName(product.getCategory())
                                  .build()));

        Collections.sort(responseList, Comparator.comparing(ProductResponse::getId,Comparator.reverseOrder()));
        int min = pageNo*pageSize;
        int max= Math.min(pageSize*(pageNo+1),responseList.size());
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,sortBy);
        return new PageImpl<>(responseList.subList(min,max),pageRequest,responseList.size());
    }

    @Override
    public Page<ProductResponse> fetchProductsBySubCategory(Long subCategoryId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Product> subCategories = productRepository.findAllByCategory_SubCategoryId(subCategoryId);
        List<ProductResponse>productResponsesList= new ArrayList<>();
        subCategories.forEach(product ->
                productResponsesList.add(
                        ProductResponse.builder()
                                .id(product.getId())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .quantityAvailable(product.getQuantityAvailable())
                                .price(product.getPrice())
                                .subCategoryName(product.getCategory())
                                .build()));
        Collections.sort(productResponsesList,Comparator.comparing(ProductResponse::getId,Comparator.reverseOrder()));
        int min = pageNo*pageSize;
        int max= Math.min(pageSize*(pageNo+1),productResponsesList.size());
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,sortBy);
        return new PageImpl<>(productResponsesList.subList(min,max),pageRequest,productResponsesList.size());

    }

    @Override
    public Page<ProductResponse> fetchProductsByBrand(Long brandId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Product> products = productRepository.findByBrandId(brandId);
        List <ProductResponse> productsList =   products.stream()
                .map( product ->
                        ProductResponse.builder()
                                .id(product.getId())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .quantityAvailable(product.getQuantityAvailable())
                                .price(product.getPrice())
                                .brandName(product.getBrand())
                                .build())
                .collect(Collectors.toList());
        Collections.sort(productsList,Comparator.comparing(ProductResponse::getCreatedAt,Collections.reverseOrder()));
        int minimum = pageNo*pageSize;
        int maximum = Math.min(pageSize*(pageNo+1),productsList.size());
        PageRequest pageable = PageRequest.of(pageNo,pageSize,Sort.Direction.DESC,sortBy);

        return new PageImpl<>(productsList.subList(minimum,maximum),pageable,productsList.size());
    }

    @Override
    public List<ProductResponse> viewNewArrival() {
        List<Product> products = productRepository.findAllProductByCreatedAtDesc();
        return products.stream().map(this::mapToProductResponse)
                 .collect(Collectors.toList());

    }

    @Override
    public List<ProductResponse> viewBestSelling() {
        List<Product> products = productRepository.findProductByBestSelling();
        return products.stream().map(this::mapToProductResponse)
                .collect(Collectors.toList());

    }


    private ProductResponse mapToProductResponse(Product product) {
         return     ProductResponse.builder()

                 .productName(product.getProductName())
                 .description(product.getDescription())
                 .price(product.getPrice())
                 .quantityAvailable(5)
                 .isOutOfStock(false)
                 .sales(product.getSales())
                 .createdAt(product.getCreatedAt())
                 .updateAt(product.getUpdatedAt())
                 .build();

    }


}
