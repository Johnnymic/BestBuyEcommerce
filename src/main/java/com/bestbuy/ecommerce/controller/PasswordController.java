package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.ResetPasswordRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;


    @PostMapping("/forget/password")
    public ResponseEntity <ApiResponse<String>>forgetPassword(@RequestParam("email") String email, HttpServletRequest request){

        ApiResponse<String> apiResponse = new ApiResponse<>(passwordService.forgetPassword(email,request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




    @PostMapping("/reset/password")
    public ResponseEntity <ApiResponse<String>>resetPassword(@RequestBody ResetPasswordRequest request){

        ApiResponse<String> apiResponse = new ApiResponse<>(passwordService.resetPassword(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}
