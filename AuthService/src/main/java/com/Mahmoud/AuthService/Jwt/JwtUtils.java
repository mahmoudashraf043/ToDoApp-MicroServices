package com.Mahmoud.AuthService.Jwt;

import com.Mahmoud.AuthService.DTO.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private final String secretKey = "asjjdfhksduwefhklawufjasblasuywquoyelrugluaygfklasuhjfgahfyquiwre";

    public Map<String,Object> generateClaims(String username , String email) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("email", email);
        return claims;
    }

    public LoginResponse generateToken(String username, String email)
    {
        LoginResponse loginResponse = new LoginResponse();
        String token = Jwts.builder()
                .setClaims(generateClaims(username , email))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        loginResponse.setToken(token);
        return loginResponse;

    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
}


