package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.JwtToken;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.enums.Roles;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.UserCredentialNotFoundException;
import com.bestbuy.ecommerce.exceptions.UserDetailedException;
import com.bestbuy.ecommerce.security.JwtService;
import com.bestbuy.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserServicesImpl implements UserService {
    private final AppUserRepository appUserRepository;
    private  final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationResquest) {
        AppUser appUser = mapToEntity(registrationResquest);
         var newAppUser = appUserRepository.save(appUser);
         return RegistrationResponse.builder()
                 .firstName(newAppUser.getFirstName())
                 .lastName(newAppUser.getLastName())
                 .message("sucessful")
                 .build();
    }
    /*

    step to authenticate a user
    first thing
    1. find the user email from the database  which return the user or else throw user disable exception
    2. checked if the user from the database  unable to login in or else thrown user account disabled
    and if the login password is not the same with what is the same user password.
    3  call the userPasswordAuthentication to return the Authentication , Object will receive the user password and the email
    4. we need to make a call to the jwtService  to call the generateToken method which return  a strings , also we need a call to the refresh token
    5. we need to build all the jwt token and set the isRevoked and isEnable method to be true;
    6. we need to revoke all user  the token by make a call to our jwtService and  call the method to have the revoked token,
    7. save the current token of the user and then authentication the email and password of the user
    *. if everything is sucessful , we want to display access token, refresh token and  name of the user and the message  to




    */

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        AppUser appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new AppUserNotFountException("user not found exception"));
            if(appUser.getIsEnabled().equals(true)){
                 throw  new UserDetailedException("User Account  not enabled");
            }
            if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())){
                throw  new UserCredentialNotFoundException("password does not match ");
           }
            Authentication authentication = new UsernamePasswordAuthenticationToken(appUser.getPassword(),appUser.getEmail());
             String  access_token = jwtService.generateToken(authentication);
             String  refresh_token = jwtService.generateRefreshToken(authentication);
            JwtToken jwtToken = JwtToken.builder()
                    .accessToken(access_token)
                    .refreshToken(refresh_token)
                    .appUser(appUser)
                    .isExpired(false)
                    .isRevoked(false)
                    .build();
            jwtService.revokedAllUserToken(appUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  LoginResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .userFullName(appUser.getFirstName() + " "+ appUser.getLastName())
                .message("success")
                .build();
    }


    private AppUser mapToEntity(RegistrationRequest requesteqquest) {
        return  AppUser.builder()
                .firstName(requesteqquest.getFirstName())
                .lastName(requesteqquest.getLastName())
                .phone(requesteqquest.getPhone())
                .password(passwordEncoder.encode(requesteqquest.getPassword()))
                .isEnabled(false)
                .roles(Roles.USER)
                .email(requesteqquest.getEmail())
                .build();
    }
}
