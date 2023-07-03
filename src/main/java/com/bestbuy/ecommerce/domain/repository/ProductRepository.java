package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.SubCategory;
import com.bestbuy.ecommerce.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

 List<Product> findAllByCategory(SubCategory category);
}
