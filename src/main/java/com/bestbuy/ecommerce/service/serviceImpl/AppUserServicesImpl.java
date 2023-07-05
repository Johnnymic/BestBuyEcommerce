package com.bestbuy.ecommerce.service.serviceImpl;


import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.JwtToken;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.JwtTokenRepository;
import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.enums.Roles;
import com.bestbuy.ecommerce.event.RegistrationCompleteEvent;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.UserCredentialNotFoundException;
import com.bestbuy.ecommerce.exceptions.UserDetailedException;
import com.bestbuy.ecommerce.security.JwtService;
import com.bestbuy.ecommerce.service.AppUserService;
import com.bestbuy.ecommerce.utitls.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserServicesImpl implements AppUserService{
    private final AppUserRepository appUserRepository;
    private  final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;

    private final JwtTokenRepository tokenRepository;

    private final JwtService jwtService;
    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationResquest, HttpServletRequest request) {
        AppUser appUser = mapToEntity(registrationResquest);

         var newAppUser = appUserRepository.save(appUser);
         eventPublisher.publishEvent(new RegistrationCompleteEvent(EmailUtils.applicationUrl(request),appUser));
         return RegistrationResponse.builder()
                 .firstName(newAppUser.getFirstName())
                 .lastName(newAppUser.getLastName())
                 .message("successful")
                 .build();
    }


    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        AppUser appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->  new AppUserNotFountException("user not found exception"));
            if(appUser.getIsEnabled().equals(true)){
                 throw  new UserDetailedException("User Account  not enabled");
            }
            if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())){
                throw  new UserCredentialNotFoundException("password does not match ");
           }
            Authentication authentication = new UsernamePasswordAuthenticationToken(appUser.getPassword(),appUser.getEmail());
             String  access_token = jwtService.generateAccessTokens(appUser);
             String  refresh_token = jwtService.generateRefreshTokens(appUser);

        JwtToken jwtToken = JwtToken.builder()
                .accessToken(access_token )
                .refreshToken(refresh_token)
                .appUser(appUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        jwtService.revokedAllUserToken(appUser);
           JwtToken token =    tokenRepository.save(jwtToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .userFullName(appUser.getFirstName() + " "+ appUser.getLastName())
                .message("successfully login")
                .build();
    }

    private void saveUserToken(AppUser appUser, String accessToken, String refresh_token) {

        JwtToken jwtToken = JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refresh_token)
                .appUser(appUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }


    private AppUser mapToEntity(RegistrationRequest requesteqquest) {
        return  AppUser.builder()
                .firstName(requesteqquest.getFirstName())
                .lastName(requesteqquest.getLastName())
                .phone(requesteqquest.getPhone())
                .password(passwordEncoder.encode(requesteqquest.getPassword()))
                .date_of_birth(requesteqquest.getDateOfBirth())
                .isEnabled(false)
                .roles(Roles.USER)
                .gender(requesteqquest.getGender())
                .email(requesteqquest.getEmail())
                .build();
    }



}
