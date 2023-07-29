package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.config.CloudinaryConfig;
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
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository categoryRepository;

    private final CloudinaryConfig cloudinaryConfig;

    @Override
    public ProductResponse getProductByCategoryId(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found exception"));

        SubCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (!product.getCategory().getSubCategoryId().equals(category.getSubCategoryId())) {
            throw new ProductNotFoundException("product not belonging to category", HttpStatus.BAD_REQUEST);
        }
        return mapToProductResponse(product);
    }

    @Override
    public List<ProductResponse> fetchAllProducts() {
        List<Product> fetchAllProduct = productRepository.findAll();
        return fetchAllProduct.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> fetchProductByPaginatedAndSorted(Integer pageNo, Integer pageSize, String sortBy, boolean ascending) {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> responseList = new ArrayList<>();
        productList.forEach(product ->
                responseList.add(
                        ProductResponse.builder()
                                .id(product.getId())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .quantityAvailable(product.getQuantityAvailable())
                                .price(product.getPrice())
                                .brandName(Brand.builder()
                                                .id(product.getBrand().getId())
                                                .brandDescription(product.getBrand()
                                                        .getBrandDescription())
                                                .brandName(product.getBrand()
                                                .getBrandName())
                                        .logoUrl(product.getImageUrl())
                                        .build())
                                .subCategoryName(SubCategory.builder()
                                        .subCategoryId(product.getId())
                                        .imageUrl(product.getImageUrl())
                                        .subCategoryName(product.getCategory().
                                                getSubCategoryName()).build())
                                .build()));

        Collections.sort(responseList, Comparator.comparing(ProductResponse::getId, Comparator.reverseOrder()));
        int min = pageNo * pageSize;
        int max = Math.min(pageSize * (pageNo + 1), responseList.size());
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        return new PageImpl<>(responseList.subList(min, max), pageRequest, responseList.size());
    }

    @Override
    public Page<ProductResponse> fetchProductsBySubCategory(Long subCategoryId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Product> subCategories = productRepository.findAllByCategory_SubCategoryId(subCategoryId);
        List<ProductResponse> productResponsesList = new ArrayList<>();
        subCategories.forEach(product ->
                productResponsesList.add(
                        ProductResponse.builder()
                                .id(product.getId())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .quantityAvailable(product.getQuantityAvailable())
                                .price(product.getPrice())
                                .subCategoryName(SubCategory.builder()
                                        .subCategoryId(product.getId())
                                        .imageUrl(product.getImageUrl())
                                        .subCategoryName(product.getCategory()
                                                .getSubCategoryName()).build())
                                .build()));
        Collections.sort(productResponsesList, Comparator.comparing(ProductResponse::getId, Comparator.reverseOrder()));
        int min = pageNo * pageSize;
        int max = Math.min(pageSize * (pageNo + 1), productResponsesList.size());
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        return new PageImpl<>(productResponsesList.subList(min, max), pageRequest, productResponsesList.size());

    }

    @Override
    public Page<ProductResponse> fetchProductsByBrand(Long brandId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Product> products = productRepository.findByBrandId(brandId);
        List<ProductResponse> productsList = products.stream()
                .map(product ->
                        ProductResponse.builder()
                                .id(product.getId())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .quantityAvailable(product.getQuantityAvailable())
                                .price(product.getPrice())
                                .brandName(Brand.builder().brandName(product.getBrand().getBrandName()).build())
                                .build())
                .collect(Collectors.toList());
        Collections.sort(productsList, Comparator.comparing(ProductResponse::getCreatedAt, Collections.reverseOrder()));
        int minimum = pageNo * pageSize;
        int maximum = Math.min(pageSize * (pageNo + 1), productsList.size());
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);

        return new PageImpl<>(productsList.subList(minimum, maximum), pageable, productsList.size());
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

    @Override
    public Object uploadProductPic(Long productId, MultipartFile productImage) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("product not found"));
        String productImageUrl = uploadImage(productImage);

        product.setImageUrl(productImageUrl);
        productRepository.save(product);
        return productImage;
    }

    @Override
    public String deleteProductPic(Long productId) throws IOException {
        try {
            cloudinaryConfig.cloudinary().uploader().destroy(String.valueOf(productId), ObjectUtils.emptyMap());
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        return "image successfully deleted";
    }

    @Override
    public String uploadProductPicWithOutId(MultipartFile productImage) throws IOException {
        return uploadImage(productImage);
    }



    private String uploadImage(MultipartFile productImage) throws IOException {
       try {
           File file = convertToMultiplePartFile(productImage);
           Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(file, ObjectUtils.asMap("use_file_name", true, "unique_fileName", true));
           boolean isDeletedFile = file.delete();
           if (isDeletedFile) {
               log.info("File successfully deleted");
           } else
               log.info("File doesn't exist");
           return  uploadResult.get("url").toString();
       }
       catch (IOException e){
           throw new IOException(e);
       }

    }

    private File convertToMultiplePartFile(MultipartFile productImage) throws IOException {
        String file = productImage.getOriginalFilename();
        if(file==null){
            throw  new AssertionError();
        }
        File fileConverter = new File(file);
        FileOutputStream fileOutputStream = new FileOutputStream(fileConverter);
        fileOutputStream.write(productImage.getBytes());
        fileOutputStream.close();
        return fileConverter;
    }


    private ProductResponse mapToProductResponse(Product product) {
         return     ProductResponse.builder()

                 .productName(product.getProductName())
                 .description(product.getDescription())
                 .price(product.getPrice())
                 .quantityAvailable(5)
                 .isOutOfStock(false)
                 .sales(product.getSales())
//                 .createdAt(product.getCreatedAt())
//                 .updateAt(product.getUpdatedAt())
                 .build();

    }


}
