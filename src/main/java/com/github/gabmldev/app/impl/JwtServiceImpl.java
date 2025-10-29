package com.github.gabmldev.app.impl;

import com.github.gabmldev.app.services.JwtService;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String issuer = "lpii-server";
    private static final long EXPIRATION = 1000 * 60 * 60;

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    @Override
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION);
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .header().keyId("mainKey").and()
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiry)
                .subject(subject)
                .claims(claims)
                .signWith(key)
                .id(jti)
                .compact();

    }

    @Override
    public String extractUsername(String token) {
        return parseClaims(token).getPayload().getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }
}
