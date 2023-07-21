package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.entity.Amount;
import com.bestbuy.ecommerce.domain.entity.Transactions;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.InitializePaymentResponse;
import com.bestbuy.ecommerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private  final TransactionService  transactionService;


    @PostMapping("/customer/payment")
    public ResponseEntity<ApiResponse<JSONObject>>initializePayment(Amount amount)  {
        ApiResponse<JSONObject> apiResponse = new ApiResponse<>(transactionService.initializePayment(amount));

        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/verify/transaction/{reference}")
    public ResponseEntity<ApiResponse<InitializePaymentResponse>>confirmPayment(@PathVariable String reference){
        ApiResponse<InitializePaymentResponse> apiResponse = new ApiResponse<>(transactionService.verifyPayment(reference));

        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping("/finalize/payment")
    public ResponseEntity<ApiResponse<Object>> finalizePayment(@RequestParam("reference") String reference){
        ApiResponse<Object> apiResponse = new ApiResponse<>(transactionService.finalizeTransaction(reference));

        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
