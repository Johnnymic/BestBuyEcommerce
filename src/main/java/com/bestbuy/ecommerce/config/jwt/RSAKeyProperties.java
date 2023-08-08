package com.bestbuy.ecommerce.config.jwt;

import org.springframework.stereotype.Component;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class RSAKeyProperties {

    private RSAPublicKey rsaPublicKey;
    private RSAPrivateKey rsaPrivateKey;

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(RSAPublicKey rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public RSAPrivateKey getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(RSAPrivateKey rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public void loadKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.rsaPublicKey = publicKey;
        this.rsaPrivateKey = privateKey;
    }
}
