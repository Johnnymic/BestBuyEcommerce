package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
  boolean existsByProductName(String productName);

    List<Product> findAllByCategory(SubCategory category);

    @Query(value = "SELECT * FROM products WHERE subcategory_id=?", nativeQuery=true)
    List<Product>findAllByCategory_SubCategoryId(Long subcategoryId);

    @Query(value = "SELECT * FROM products WHERE brand_id=?", nativeQuery=true)
    List<Product> findByBrandId(Long brandId);

    @Query(value = "SELECT * FROM products  ORDER BY  createdAt DESC LIMIT 5", nativeQuery = true)
    List<Product> findAllProductByCreatedAtDesc();

    @Query(value = "SELECT * FROM products ORDER BY sale ASC LIMIT 5",nativeQuery = true)
    List<Product> findProductByBestSelling();
}
