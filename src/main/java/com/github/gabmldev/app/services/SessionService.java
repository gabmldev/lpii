package com.github.gabmldev.app.services;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.github.gabmldev.app.entity.Session;

public interface SessionService {
    public Optional<Map<String, Session>> findAllSessions(UUID userId);

    public Optional<Session> findSession(UUID userId, String jti);

    public Session getCurrentSession(UUID userId);

    public boolean verifySession(UUID userId, String jti);

    public void deleteSession(UUID userId, String jti);

    public void deleteExpiredSessions(UUID userId);

    public void saveSession(Session session);

    public void createSession(
            UUID userId,
            String jti,
            String token,
            LocalDateTime createdAt,
            LocalDateTime expiresAt);
}
