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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private  final AppUserService userService;

    private VerificationTokenService verificationTokenService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse>registerUser(@RequestBody RegistrationRequest registrationResquest) {
        RegistrationResponse registerUser = userService.registerUser(registrationResquest);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse>authenticate(@RequestBody LoginRequest loginRequest){
        LoginResponse loginUser= userService.authenticateUser(loginRequest);
        return  new ResponseEntity<>(loginUser,HttpStatus.OK);
    }

    @GetMapping ("/resend-verification")
    public ResponseEntity<ApiResponse<String>>verifyUser(@RequestParam("token")
                                                             String token, HttpServletRequest request){

        ApiResponse<String> apiResponse =  new ApiResponse<>(verificationTokenService.verifyUser(token, request));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);

    }




}
