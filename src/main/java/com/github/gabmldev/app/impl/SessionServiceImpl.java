package com.github.gabmldev.app.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.gabmldev.app.entity.Session;
import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.repository.SessionRepository;
import com.github.gabmldev.app.services.SessionService;

import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.MissingClaimException;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Optional<Map<String, Session>> findAllSessions(UUID userId) {
        return Optional.of(
                sessionRepository
                        .findAllByUserId(userId)
                        .orElse(List.of())
                        .stream()
                        .collect(Collectors.toMap(Session::getJti, Function.identity())));
    }

    @Override
    public Optional<Session> findSession(UUID userId, String jti) {
        return sessionRepository.findByUserIdAndJti(userId, jti);
    }

    @Override
    public void createSession(
            UUID userId,
            String jti,
            String token,
            LocalDateTime createdAt,
            LocalDateTime expiresAt) {
        Session session = Session.builder()
                .jti(jti)
                .token(token)
                .user(User.builder().id(userId).build())
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .build();
        sessionRepository.save(session);
    }

    @Override
    public Session getCurrentSession(UUID userId) {
        return sessionRepository.findByUserId(userId);
    }

    @Override
    public boolean verifySession(UUID userId, String jti)
            throws MissingClaimException, IncorrectClaimException {
        Optional<Session> optSession = findSession(userId, jti);
        if (optSession.isEmpty()) {
            return false;
        }
        Session session = optSession.get();

        return !session.getExpiresAt().isBefore(LocalDateTime.now());
    }

    @Override
    public void deleteSession(UUID userId, String jti) {
        sessionRepository.deleteSession(userId, jti);
    }

    @Override
    public void deleteExpiredSessions(UUID userId) {
        sessionRepository.deleteExpiredSessions(userId, LocalDateTime.now());
    }

    @Override
    public void saveSession(Session session) {
        sessionRepository.save(session);
    }
}
