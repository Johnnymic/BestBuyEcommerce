package com.bestbuy.ecommerce.config.jwt;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;


@Configuration
@RequiredArgsConstructor
//@DependsOn(value = "rsaKeyProperties")
public class JwtCoder {

    private  final RSAKeyProperties rsaKeyProperties
            ;

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.
                withPublicKey(rsaKeyProperties.getRsaPublicKey())
                .build();
    }
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeyProperties
                .getRsaPublicKey())
                .privateKey(rsaKeyProperties.getRsaPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource =new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }



}
