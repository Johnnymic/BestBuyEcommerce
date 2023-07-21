package com.bestbuy.ecommerce.domain.repository;

import com.bestbuy.ecommerce.domain.entity.PayStackTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PayStackTransactions, Long> {
    Optional<PayStackTransactions> findByReference(String reference);




}
