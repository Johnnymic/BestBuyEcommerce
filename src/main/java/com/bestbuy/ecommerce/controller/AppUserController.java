package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.dto.request.EditProfileRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.EditProfileResponse;
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
public class AppUserController {

    private final AppUserService userService;
    private  final VerificationTokenService verificationTokenService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponse>> registerUser(@Validated @RequestBody RegistrationRequest registrationResquest, HttpServletRequest request) {
        ApiResponse<RegistrationResponse> registerUser =  new ApiResponse<>(userService.registerUser(registrationResquest,request));
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<ApiResponse<String>>verifyUser(@PathVariable
                                                         String token, HttpServletRequest request){

        ApiResponse<String> apiResponse =  new ApiResponse<>(verificationTokenService.verifyUser(token, request));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);

    }
    @GetMapping ("/resend-new_verification")
    public ResponseEntity<ApiResponse<String>>resendVerificationLink(@RequestParam("email")
                                                                     String email, HttpServletRequest request){

        ApiResponse<String> apiResponse =  new ApiResponse<>(verificationTokenService.sendUserVerficationMail(email, request));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);

    }

    @PutMapping ("/edit-profile")
    public ResponseEntity<ApiResponse<EditProfileResponse>>editProfile(@Validated
                                                                           @RequestBody EditProfileRequest editProfileRequest){

        ApiResponse<EditProfileResponse> apiResponse =  new ApiResponse<>(userService.editProfile(editProfileRequest));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
//    @GetMapping
//    public ResponseEntity<ApiResponse<UserProfileResponse>> viewUserProfile(){
//        ApiResponse<UserProfileResponse> response = new ApiResponse<UserProfileResponse>()
//    }




}
