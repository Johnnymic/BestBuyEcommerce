package com.bestbuy.ecommerce.service.serviceImpl;


import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.JwtToken;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.JwtTokenRepository;
import com.bestbuy.ecommerce.dto.request.EditProfileRequest;
import com.bestbuy.ecommerce.dto.request.LoginRequest;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.EditProfileResponse;
import com.bestbuy.ecommerce.dto.responses.LoginResponse;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.dto.responses.UserProfileResponse;
import com.bestbuy.ecommerce.enums.Gender;
import com.bestbuy.ecommerce.enums.Roles;
import com.bestbuy.ecommerce.event.RegistrationCompleteEvent;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.UserCredentialNotFoundException;
import com.bestbuy.ecommerce.exceptions.UserDetailedException;
import com.bestbuy.ecommerce.security.JwtService;
import com.bestbuy.ecommerce.service.AppUserService;
import com.bestbuy.ecommerce.utitls.EmailUtils;
import com.bestbuy.ecommerce.utitls.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public EditProfileResponse editProfile(EditProfileRequest editProfileRequest) {
          AppUser loginUser= appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                  .orElseThrow(()-> new AppUserNotFountException("user not found"));
           loginUser.setFirstName(editProfileRequest.getFirstName()) ;
           loginUser.setEmail(editProfileRequest.getEmail());
           loginUser.setLastName(editProfileRequest.getLastName());
           loginUser.setDate_of_birth(editProfileRequest.getDate_of_birth());
           loginUser.setPhone(editProfileRequest.getPhone());
           loginUser.setGender(editProfileRequest.getGender());
            var newUserProfile = appUserRepository.save(loginUser);
        return mapToEditProfileResponse(newUserProfile);
    }

    @Override
    public UserProfileResponse viewUserProfile() {
        AppUser loginUser= appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("user not found"));

        return mapToUserProfileResponse(loginUser);
    }

    @Override
    public Page<UserProfileResponse> viewAllUserProfilesByPaginationAndSort(Integer pageNo,
                                                                            Integer pageSize,
                                                                            String sortBy) {
        List<AppUser> userPage = appUserRepository.findAll();
        List<UserProfileResponse> customerProfile =userPage.stream()
                .map(person-> UserProfileResponse.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .email(person.getEmail())
                        .phone(person.getPhone())
                        .date_of_birth(person.getDate_of_birth())
                        .gender(person.getGender())
                        .build())
                .collect(Collectors.toList());
             PageRequest    pageRequest    =PageRequest.of(pageNo,pageSize, Sort.Direction.DESC, sortBy);
             int maxPage = Math.min(pageSize * (pageNo+1),customerProfile.size());
             return new PageImpl<>(customerProfile.subList(pageNo*pageSize,maxPage),pageRequest,customerProfile.size());


    }

    private UserProfileResponse mapToUserProfileResponse(AppUser loginUser) {
        return UserProfileResponse.builder()
                .firstName(loginUser.getFirstName())
                .lastName(loginUser.getLastName())
                .email(loginUser.getEmail())
                .phone(loginUser.getPhone())
                .date_of_birth(loginUser.getDate_of_birth())
                .gender(loginUser.getGender())
                .build();
    }

    private EditProfileResponse mapToEditProfileResponse(AppUser newUserProfile) {
        return EditProfileResponse.builder()
                .firstName(newUserProfile.getFirstName())
                .lastName(newUserProfile.getLastName())
                .email(newUserProfile.getEmail())
                .date_of_birth(newUserProfile.getDate_of_birth())
                .phone(newUserProfile.getPhone())
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
                .gender(Gender.MALE)
                .email(requesteqquest.getEmail())
                .build();
    }



}
