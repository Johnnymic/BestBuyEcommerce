package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.VerificationTokenRepository;
import com.bestbuy.ecommerce.dto.request.ResetPasswordRequest;
import com.bestbuy.ecommerce.event.password.ForgetPasswordEvent;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.SamePasswordException;
import com.bestbuy.ecommerce.service.PasswordService;
import com.bestbuy.ecommerce.utils.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImp implements PasswordService {

    private  final AppUserRepository appUserRepository;

    private final ApplicationEventPublisher publisher;

    private   HttpServletRequest request ;
    private final  PasswordEncoder passwordEncoder;

    private  final VerificationTokenRepository verificationTokenRepository;
    @Override
    public String forgetPassword(String email, HttpServletRequest request) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(()-> new  AppUserNotFountException("user not found exception"));
        VerificationToken token = verificationTokenRepository.findByAppUser(appUser);
        if(token==null){
            verificationTokenRepository.delete(token);
        }
        publisher.publishEvent(new ForgetPasswordEvent(EmailUtils.forgetPasswordUrl(request),appUser));
        return "Please Check Your Mail For Password Reset Link";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        AppUser appUser = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new  AppUserNotFountException("user not found exception"));
        if(passwordEncoder.matches(appUser.getPassword(), request.getPassword() )){

            throw new SamePasswordException("Please Choose a Different Password");
        }
        appUser.setPassword(request.getPassword());
        appUserRepository.save(appUser);
        return "password is successfully changed";
    }


}
