package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.AddressRequest;
import com.bestbuy.ecommerce.dto.responses.AddressResponse;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.service.AddressServices;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressServices addressServices;

   @PostMapping("/new/address/")
    public ResponseEntity<ApiResponse<AddressResponse>> addNewAddress(@RequestBody AddressRequest addressRequest){
      ApiResponse<AddressResponse> apiResponse = new ApiResponse<>(addressServices.addNewAddress(addressRequest));
      return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view/address/{addressId}")
    public  ResponseEntity<ApiResponse<AddressResponse>>viewAddress(@PathVariable("addressId") Long addressId) {
        ApiResponse<AddressResponse> apiResponse = new ApiResponse<>(addressServices.viewAddress(addressId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(@PathVariable("addressId")Long addressId ,@RequestBody AddressRequest addressRequest){
        ApiResponse<AddressResponse> apiResponse = new ApiResponse<>(addressServices.updateAddress(addressId,addressRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view/all/address")
    public ResponseEntity<ApiResponse <List<AddressResponse>>> viewAllAddresses(){
        ApiResponse <List<AddressResponse>> apiResponse = new ApiResponse<>(addressServices.viewAllAddresses());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/address/{addressId}")
    public  ResponseEntity<ApiResponse<String>>deleteAddress(@PathVariable("addressId") Long addressId) {
        ApiResponse<String> apiResponse = new ApiResponse<>(addressServices.deleteAddress(addressId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }





}

