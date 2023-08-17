package com.bestbuy.ecommerce.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "rsa")
public class RSAKeyProperties {


    private RSAPublicKey rsaPublicKey;

    private RSAPrivateKey rsaPrivateKey;
}
