package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    @Query(value = "SELECT * FROM reviews WHERE product_id=?", nativeQuery = true)
    List<Reviews>findReviewsByProduct(Long productId);
}
