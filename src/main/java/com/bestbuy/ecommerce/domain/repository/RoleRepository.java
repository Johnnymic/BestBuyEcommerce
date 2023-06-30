package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
