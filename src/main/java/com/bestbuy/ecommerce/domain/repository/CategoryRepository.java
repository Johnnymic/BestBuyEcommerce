package com.bestbuy.ecommerce.domain.repository;


import com.bestbuy.ecommerce.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
