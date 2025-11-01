package com.github.gabmldev.app.services.impl;

import com.github.gabmldev.app.utils.UserClaims;
import com.github.gabmldev.app.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String ISSUER = "lpii-server";
    private static final long EXPIRATION = (long) 1000 * 120;

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    @Override
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION);
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
            .header()
            .keyId("mainKey")
            .and()
            .issuer(ISSUER)
            .issuedAt(now)
            .expiration(expiry)
            .subject(subject)
            .claims(claims)
            .signWith(key)
            .id(jti)
            .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractJti(String token) {
        return extractClaim(token, claims -> claims.get("jti", String.class));
    }

    @Override
    public UserClaims extractUserClaims(String token) {
        return extractClaim(token, claims ->
            claims.get("data", UserClaims.class)
        );
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private <T> T extractClaim(
        String token,
        Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
