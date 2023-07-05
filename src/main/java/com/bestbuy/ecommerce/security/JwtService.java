package com.bestbuy.ecommerce.security;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.JwtToken;
import com.bestbuy.ecommerce.domain.repository.JwtTokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class JwtService {

    private  final JwtTokenRepository jwtTokenRepository;

    private UserDetailsService userDetailsService;

    @Value("${jwt.expiration.access-token}")
    private long access_expiration;

    @Value("${jwt.expiration.refresh-token}")
    private long  refresh_token;
 // headers
    private Key generatedKey() {
        byte[]  secretKey = DatatypeConverter.parseBase64Binary(generateSecret());
        return new SecretKeySpec(secretKey, "HmacSHA512");
    }
    private String generateSecret() {
        return DatatypeConverter.printBase64Binary(new byte[512/8]);
    }
    //payloader
    public  String extractUsername(String token){
          return  extractClaim(token,Claims::getSubject);

    }
    private Date extractExpiration(String token){
    return  extractClaim(token,Claims::getExpiration);

    }
    private  <T> T extractClaim(String token , Function<Claims, T> claimsRevolver){
        Claims claims =extractAllClaims(token);
       return  claimsRevolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
     return Jwts.parserBuilder()
             .setSigningKey(generatedKey())
             .build()
             .parseClaimsJws(token)
             .getBody();
    }

    // generate the token
    private boolean isTokenExpired(String token){
        return  extractExpiration(token).after(new Date());

    }

    public String generateToken( Map<String,Object> extractClaims,
                                        UserDetails userDetails){
        return  buildToken(extractClaims, userDetails, access_expiration);
    }

public String generateAccessTokens(Authentication authentication){
    String username = authentication.getName();
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + access_expiration))
            .signWith(generatedKey(), SignatureAlgorithm.HS256)
            .compact();
}
    public String generateAccessTokens(UserDetails userDetails){
        return  buildToken(new HashMap<>(), userDetails, access_expiration);
    }
      public String generateRefreshTokens(UserDetails userDetails){
        return  buildToken(new HashMap<>(), userDetails, refresh_token);
      }
    public String buildToken(Map<String, Object> extractClaims,
                             UserDetails userDetails,long expiration


     ){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generatedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void revokedAllUserToken(AppUser appUser){
        List<JwtToken> jwtTokensValidation = jwtTokenRepository.findAllValidTokenByUser(appUser.getId());
        if (!jwtTokensValidation.isEmpty()){
             jwtTokensValidation.forEach(
                     t-> {
                         t.setRevoked(true);
                         t.setExpired(true);
                     });
                 }
        jwtTokenRepository.saveAll(jwtTokensValidation);
            }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }



}
