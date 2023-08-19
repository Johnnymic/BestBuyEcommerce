package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.Address;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository  extends JpaRepository<Address,Long> {

    Optional<Address> findByAndEmailAddress(String email);

    @Query(value = "select  u from Address  u where u.street = :streetName ")
    Address findByStreet(@Param("streetName") String streetName);


}
