package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository  extends JpaRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByToken(String token);

   VerificationToken findByAppUser(AppUser appUser);

    boolean existsByAppUser(AppUser appUser);
}
