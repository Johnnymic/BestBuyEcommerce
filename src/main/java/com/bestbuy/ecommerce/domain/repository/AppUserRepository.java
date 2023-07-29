package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    boolean existsByEmail (String email);
    Optional<AppUser> findByEmail(String email);


}
