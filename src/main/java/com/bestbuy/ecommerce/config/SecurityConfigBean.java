package com.bestbuy.ecommerce.config;

import com.bestbuy.ecommerce.config.jwt.JwtCoder;
import com.bestbuy.ecommerce.config.jwt.RSAKeyProperties;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigBean {
    private final AppUserRepository appUserRepository;

    private static final String AUTHORITY_PREFIX = "ROLE_";
    private static final String CLAIM_ROLES = "roles";

    private final JwtCoder jwtEncoder;




    @Bean
    public UserDetailsService userDetailsService(){
      return  username->appUserRepository.findByEmail(username)
                .orElseThrow( ()-> new UsernameNotFoundException("user not found"));

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider  authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return  authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
     return    configuration.getAuthenticationManager();
    }

   public static Converter<Jwt,? extends AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(getJwtGrantedAuthorityConverter());
         return jwtAuthenticationConverter;
    }

    private static Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthorityConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
        grantedAuthoritiesConverter.setAuthoritiesClaimName(CLAIM_ROLES);
        return grantedAuthoritiesConverter;

    }
//    @Bean
//    BeanFactoryPostProcessor conversionServiceCustomizer() {
//        return beanFactory ->
//                beanFactory.getBean(RsaKeyConversionServicePostProcessor.class)
//                        .setResourceLoader(new CustomResourceLoader());
//    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
       JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtEncoder.jwtDecoder());
       provider.setJwtAuthenticationConverter(getJwtAuthenticationConverter());
       return provider;
    }



}
