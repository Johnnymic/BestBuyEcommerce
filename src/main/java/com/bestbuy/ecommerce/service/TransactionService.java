package com.bestbuy.ecommerce.service;

import com.bestbuy.ecommerce.domain.entity.Amount;
import com.bestbuy.ecommerce.dto.responses.InitializePaymentResponse;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface TransactionService {
    JSONObject initializePayment(Amount amount);

    InitializePaymentResponse verifyPayment(String reference);

    Object finalizeTransaction(String reference);
}
