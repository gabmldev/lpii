package com.github.gabmldev.app.services;

import com.github.gabmldev.app.utils.UserClaims;
import java.util.Map;

public interface JwtService {
    public String generateToken(String subject, Map<String, Object> claims);
    public String extractUsername(String token);
    public String extractJti(String token);
    public UserClaims extractUserClaims(String token);
    public boolean validateToken(String token);
}
