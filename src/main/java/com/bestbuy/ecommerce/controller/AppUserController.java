package com.bestbuy.ecommerce.controller;

import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.dto.request.EditProfileRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.ApiResponse;
import com.bestbuy.ecommerce.dto.responses.EditProfileResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.dto.responses.UserProfileResponse;
import com.bestbuy.ecommerce.service.AppUserService;
import com.bestbuy.ecommerce.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class AppUserController {

    private final AppUserService userService;
    private  final VerificationTokenService verificationTokenService;




    @GetMapping("/token/{token}")
    public ResponseEntity<ApiResponse<String>>verifyUser(@PathVariable
                                                         String token, HttpServletRequest request){

        ApiResponse<String> apiResponse =  new ApiResponse<>(verificationTokenService.verifyUser(token, request));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);

    }
    @GetMapping ("/resend/new/verification")
    public ResponseEntity<ApiResponse<String>>resendVerificationLink(@RequestParam("email")
                                                                     String email, HttpServletRequest request){

        ApiResponse<String> apiResponse =  new ApiResponse<>(verificationTokenService.sendUserVerficationMail(email, request));
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);

    }

    @PutMapping ("/edit/profile")
    public ResponseEntity<ApiResponse<EditProfileResponse>>editProfile(@Validated
                                                                           @RequestBody EditProfileRequest editProfileRequest){

        ApiResponse<EditProfileResponse> apiResponse =  new ApiResponse<>(userService.editProfile(editProfileRequest));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
    @GetMapping("/view/user/profile/")
    public ResponseEntity<ApiResponse<UserProfileResponse>> viewUserProfile(){
        ApiResponse<UserProfileResponse> response = new ApiResponse<>(userService.viewUserProfile());
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/view/user/paginated")
    public ResponseEntity<ApiResponse <Page<UserProfileResponse>>>viewAllUserProfilesByPaginationAndSort(@RequestParam(defaultValue = "0")Integer pageNo,
                                                                                                         @RequestParam(defaultValue = "16")Integer pageSize,
                                                                                                         @RequestParam(defaultValue = "id") String sortBy
                                                                                                         ){
        ApiResponse<Page<UserProfileResponse>> response = new ApiResponse<>( userService.viewAllUserProfilesByPaginationAndSort(pageNo,pageSize,sortBy));
        return  new ResponseEntity<>(response,HttpStatus.OK);

    }



}
