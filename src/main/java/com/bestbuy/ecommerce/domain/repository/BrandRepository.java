package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Long> {
    boolean existsByBrandName(String brandName);


    Optional<Brand> findByBrandName(String brandName);
}
