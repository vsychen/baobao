package com.baobao.shop.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String secret = "bao-bao-recife-faz-o-melhor-bao-do-nordeste";
    private final long jwtExpirationMs = 86400000; // 1 dia

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // public String generateToken(Authentication authentication) {
    //     User userPrincipal = (User) authentication.getPrincipal();
    //     return Jwts.builder()
    //             .setSubject(userPrincipal.getUsername())
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
    //             .signWith(getSigningKey(), SignatureAlgorithm.HS512)
    //             .compact();
    // }

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(key)
                .compact();
    }

    public String validateTokenAndGetUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}