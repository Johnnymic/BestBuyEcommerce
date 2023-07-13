package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository  extends JpaRepository<Address,Long> {

    Optional<Address> findByAndEmailAddress(String email);
}
