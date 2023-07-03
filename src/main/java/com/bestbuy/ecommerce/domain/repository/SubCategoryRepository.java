package com.bestbuy.ecommerce.domain.repository;


import com.bestbuy.ecommerce.domain.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
}
