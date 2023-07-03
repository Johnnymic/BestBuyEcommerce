package com.bestbuy.ecommerce.security;

import io.jsonwebtoken.ExpiredJwtException;

import com.bestbuy.ecommerce.domain.repository.JwtTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final JwtTokenRepository tokenRepository;

    private  final UserDetailsService userService;
    @Override
    protected void doFilterInternal(@NotNull  HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                   @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");
        String accessToken=null;
        String username = null;

        if(authHeader!=null && authHeader.startsWith("Bearer")){
          accessToken= authHeader.substring(7);


           username= jwtService.extractUsername(accessToken);
    }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserDetails userDetails =userService.loadUserByUsername(username);
            var isTokenValid = tokenRepository.findByAccessToken(accessToken)
                    .map(t->!t.isExpired() && !t.isRevoked())
                    .orElse(false);

        if(jwtService.isTokenValid(accessToken,userDetails) && isTokenValid){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
             }

        }

        filterChain.doFilter(request,response);
    }



}
