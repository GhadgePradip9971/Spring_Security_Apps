package com.employee.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;



@Service
public class JwtService {
	

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    
    private SecretKey getSigningKey() {
		byte[] keyBytes = java.util.Base64.getDecoder().decode(secret);
		return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
	}
    
    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                    new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(getSigningKey())
                .compact();
	}
    
    
    

    @PostConstruct // This method runs after Spring initializes the bean
    public void init() {
        System.out.println("JWT Secret loaded: " + (secret != null ? "YES" : "NO"));
        System.out.println("JWT Expiration: " + expiration);
    }

}
