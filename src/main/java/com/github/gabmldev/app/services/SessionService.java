package com.github.gabmldev.app.services;

import com.github.gabmldev.app.entity.Session;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface SessionService {
    public Optional<Map<String, Session>> findAllSessions(String userId);
    public Optional<Session> findSession(String userId, String jti);
    public Session getCurrentSession(String userId);
    public boolean verifySession(String userId, String jti);
    public void deleteSession(String userId, String jti);
    public void deleteExpiredSessions(String userId);
    public void saveSession(Session session);
    public void createSession(
        String userId,
        String jti,
        String token,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
    );
}
