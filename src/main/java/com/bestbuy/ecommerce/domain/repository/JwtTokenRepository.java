package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    @Query("""
         select  t from JwtToken t inner join AppUser u on t.appUser.id = u.id
         where u.id = :appUserId and  (t.isExpired= false or t.isRevoked=false)
          """)
    List<JwtToken>findAllValidTokenByUser(Long  appUserId);
    Optional<JwtToken> findByAccessToken(String accessToken);
}
