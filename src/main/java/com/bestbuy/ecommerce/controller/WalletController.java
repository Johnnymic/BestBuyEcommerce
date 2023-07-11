package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.WalletResponse;
import com.bestbuy.ecommerce.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/fund-customer-wallet/")
    public ResponseEntity<ApiResponse<WalletResponse>> fundUserWallet(@RequestBody WalletRequest walletRequest){
        ApiResponse<WalletResponse>apiResponse = new ApiResponse<>(walletService.fundCustomerWallet(walletRequest));
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }




}
