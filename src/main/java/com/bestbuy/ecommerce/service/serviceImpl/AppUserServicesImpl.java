package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.dto.request.RegistrationRequest;
import com.bestbuy.ecommerce.dto.responses.RegistrationResponse;
import com.bestbuy.ecommerce.enums.Roles;
import com.bestbuy.ecommerce.security.JwtService;
import com.bestbuy.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
         String token = jwtService.generateToken((Authentication) appUser);

         return RegistrationResponse.builder()
                 .firstName(newAppUser.getFirstName())
                 .lastName(newAppUser.getLastName())
                 .build();
    }

//    private void saveUserToken(String token, AppUser newAppUser) {
//        return Token
//    }


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
