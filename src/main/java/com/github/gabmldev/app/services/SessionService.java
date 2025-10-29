package com.github.gabmldev.app.services;

import com.github.gabmldev.app.entity.Session;

import java.util.Map;
import java.util.Optional;

public interface SessionService {
    public Optional<Map<String, Session>> findAllSessions(String userId);
    public Optional<Session> findSession(String userId, String jti);
    public void verifySession();
    public void deleteSession();
    public void updateSession();
    public void saveSession();
    public void createSession();
}
