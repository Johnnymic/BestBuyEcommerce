package com.bestbuy.ecommerce.event.password;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.VerificationToken;
import com.bestbuy.ecommerce.domain.repository.VerificationTokenRepository;
import com.bestbuy.ecommerce.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class ForgetPasswordListenEvent implements ApplicationListener<ForgetPasswordEvent> {


    private  final VerificationTokenService verificationTokenService;

    private  final JavaMailSender javaMailSender;



    @Override
    public void onApplicationEvent(ForgetPasswordEvent event) {
        String token = UUID.randomUUID().toString();
        AppUser user = event.getAppUser();
        VerificationToken verifiedToken = new VerificationToken(token,user);
         verificationTokenService.saveConfirmationToken(verifiedToken);
         String url = event.getEventUrl() + "/tokens?token=" + token;
        try{
            sendConfirmationToken(url,user);
        }catch(UnsupportedEncodingException | MessagingException e){
            throw new RuntimeException(e.getMessage());
        }

    }


    public void sendConfirmationToken(String url, AppUser appUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request";
        String senderName = "BestBuy E-commerce";
        String mailContent = "<p> Hi, "+appUser.getFirstName()+" </p>"+
                "<p> Welcome to BestBuy. </p>"+
                "<p>Please, follow the link below to reset your password. </p>" +
                "<a href=\""+url+"\"> Click here</a>" +
                "<p> Thank you <br>" + senderName;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("resetpassword@eventhub.com",senderName);
        helper.setSubject(subject);
        helper.setTo(appUser.getEmail());
        helper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
    }



}
