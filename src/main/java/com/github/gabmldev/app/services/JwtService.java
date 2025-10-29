package com.github.gabmldev.app.services;

import java.util.Map;

public interface JwtService {
    public String generateToken(String subject, Map<String, Object> claims);
    public String extractUsername(String token);
    public boolean validateToken(String token);
}
