package com.bestbuy.ecommerce.service.serviceImpl;

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

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JavaMailSenderServiceImpl implements JavaMailService {

    private final JavaMailSender javaMailSender;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaMailSenderServiceImpl.class);
    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");

    @Override
    public ResponseEntity<String> sendMail(String receiverMail, String subject, String text) {
        if (!isValidEmail(receiverMail)) {
            return new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);
        }

        isEmailDomainValid(receiverMail);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setSentDate(new Date());
        mailMessage.setFrom("johnny25580@gmail.com");
        mailMessage.setTo(receiverMail);
        mailMessage.setText(text);

        try {
            LOGGER.info("Beginning of log *********");
            LOGGER.info(IMPORTANT,"Sending mail to: " + receiverMail);
            javaMailSender.send(mailMessage);
            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(IMPORTANT + e.getMessage());
            return new ResponseEntity<>("An Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void isEmailDomainValid(String receiverMail) {
        String domain = receiverMail.substring(receiverMail.lastIndexOf("@") + 1);
        int result = emailDomain(domain);
        LOGGER.info("Domain: " + domain);
        LOGGER.info("Result of domain: " + result);
    }

    private int emailDomain(String domain) {
        Hashtable<String, String> ed = new Hashtable<>();
        try {
            ed.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext context = new InitialDirContext(ed);
            Attributes attributes = context.getAttributes(domain, new String[]{"MX"});
            Attribute attr = attributes.get("MX");
            if (attr == null) return 0;
            return attr.size();
        } catch (NamingException e) {
            LOGGER.error("Error during DNS lookup: " + e.getMessage());
            throw new RuntimeException("Error occurred during DNS lookup.", e);
        }
    }

    private boolean isValidEmail(String receiveMail) {
        String regex = "^(.+)@(\\S+)$";
        return Pattern.compile(regex).matcher(receiveMail).matches();
    }
}
