package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.WalletRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.WalletBalanceResponse;

import com.bestbuy.ecommerce.dto.responses.WalletInfoResponse;
import com.bestbuy.ecommerce.dto.responses.WalletResponse;
import com.bestbuy.ecommerce.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    @GetMapping("/customer-balance/")
    public ResponseEntity<ApiResponse<WalletBalanceResponse>> getUserBalance(){
        ApiResponse<WalletBalanceResponse>apiResponse = new ApiResponse<>(walletService.getCustomerBalance());
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/customer-view-wallet-info/")
    public ResponseEntity<ApiResponse<WalletInfoResponse>> viewUserWalletInfo(){
        ApiResponse<WalletInfoResponse>apiResponse = new ApiResponse<>(walletService.viewCustomerWallet());
        return  new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }
    @GetMapping ("/view-customer-wallet-by-pagination")
    public ResponseEntity<ApiResponse <Page<WalletResponse>>>viewUserWalletByPagination(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy

    ){
        ApiResponse<Page<WalletResponse>>apiResponse = new ApiResponse<>(walletService.
                viewCustomerWalletByPagination(pageNo,pageSize,sortBy));
        return  new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }


}
