package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
