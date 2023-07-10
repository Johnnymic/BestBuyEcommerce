package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.service.AppUserService;
import com.bestbuy.ecommerce.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private  final AppUserService userService;
    @PostMapping("/authenticate")
    public ResponseEntity< ApiResponse<LoginResponse>>authenticate(@RequestBody LoginRequest loginRequest){
      ApiResponse  <LoginResponse >loginUser= new ApiResponse<>(userService.authenticateUser(loginRequest));
        return  new ResponseEntity<>(loginUser,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponse>> registerUser(@Validated @RequestBody RegistrationRequest registrationResquest, HttpServletRequest request) {
        ApiResponse<RegistrationResponse> registerUser =  new ApiResponse<>(userService.registerUser(registrationResquest,request));
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


}
