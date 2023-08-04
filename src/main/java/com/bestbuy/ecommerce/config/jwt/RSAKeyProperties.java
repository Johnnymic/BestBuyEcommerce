package com.bestbuy.ecommerce.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationPropertiesScan
@Getter
@Setter
public class RSAKeyProperties {

    private RSAPublicKey rsaPublicKey;

    private RSAPrivateKey rsaPrivateKey;
}
