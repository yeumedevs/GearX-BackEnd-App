package com.gearx.feature.security.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;
    private final String issuer;
    private final long expMinutes;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.access-exp-minutes}") long expMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.issuer = issuer;
        this.expMinutes = expMinutes;
    }

    public String generateToken(String username, String role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expMinutes * 60);

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .addClaims(java.util.Map.of("username", username, "role", role))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Optional<String> extractUsername(String token) {
        try {
            return Optional.ofNullable(parse(token).getBody().getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> extractRole(String token) {
        try {
            return Optional.ofNullable(parse(token).getBody().get("role", String.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Instant getIssuedAt(String token) {
        return parse(token).getBody().getIssuedAt().toInstant();
    }

    public Instant getExpiration(String token) {
        return parse(token).getBody().getExpiration().toInstant();
    }

    public String getIssuer() {
        return issuer;
    }
}
