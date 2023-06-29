package com.bestbuy.ecommerce.event;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import com.bestbuy.ecommerce.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationListenEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        AppUser user = event.getAppUser();
        String token = UUID.randomUUID().toString();
        VerificationToken verification_token = new VerificationToken(token,user);
        verificationTokenService.saveConfirmationToken(verification_token);
        String url = event.getApplicationUrl()+ "/api/v1/auth/token/" + token;


//            try {
//                sendNewVerificationMail(url ,user);
//            } catch (MessagingException | UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }


        log.info("click the link to verify your registration : {} " +url);
    }

    private void  sendNewVerificationMail(String url , AppUser appUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "EmailVerification";
        String senderName = "BestBuy-ShoppingMall";
        String mailContent = "<p> Hi, "+appUser.getFirstName()+" </p>"+
                "<p> Welcome to Event Hub Inc. </p>"+
                "<p>Thank you for registering with us, "+""+
                "Please, follow the link below to complete your registration. </p>" +
                "<a href=\""+url+"\"> Verify your email to activate your account</a>" +
                "<p> Thank you <br>" + senderName;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("registeration@BestBuy",senderName);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);

    }




}
