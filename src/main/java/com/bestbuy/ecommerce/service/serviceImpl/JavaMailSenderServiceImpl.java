package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.BestBuyApplication;
import com.bestbuy.ecommerce.service.JavaMailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JavaMailSenderServiceImpl implements JavaMailService {

    private  final JavaMailSender javaMailSender;

//    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BestBuyApplication.class);
//    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");
//

    @Override
    public ResponseEntity<String> sendMail(String receiverMail, String subject, String text)  {
        if(!isValidEmail(receiverMail)){
            new ResponseEntity<>("Email is not valid",HttpStatus.BAD_REQUEST);
        }

            SimpleMailMessage  mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setSentDate(new Date());
            mailMessage.setFrom("michaeljohn@gmail.com");
            mailMessage.setTo(receiverMail);
            mailMessage.setText(text);
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

//           try {
////               LOGGER.info("Beginning of log *********");
//               LOGGER.info( "Sending mail to: " + receiverMail);
//               javaMailSender.send(mailMessage);
//               return new ResponseEntity<>("Sent", HttpStatus.OK);
//           }catch (Exception e){
//               LOGGER.info("error is catch");
//           }
//         return new ResponseEntity<>("An Error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isValidEmail(String receiveMail) {
        String regex = "^(.+)@(\\S+)$";
        return Pattern.compile(regex)
                .matcher(receiveMail)
                .matches();

    }
}
