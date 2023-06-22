package com.bestbuy.ecommerce.security;

import com.bestbuy.ecommerce.domain.entity.JwtToken;
import com.bestbuy.ecommerce.domain.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtTokenRepository jwtTokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication)

    {
        String access_token = null;
        String authHeader = request.getHeader("Authorization");

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            access_token =authHeader.substring(7);
             JwtToken jwtToken= jwtTokenRepository.findByAccessToken(access_token).orElse(null);
             if(jwtToken!=null){
                 jwtToken.setRevoked(true);
                 jwtToken.setExpired(true);
                 jwtTokenRepository.save(jwtToken);
             }
        }

    }
}
