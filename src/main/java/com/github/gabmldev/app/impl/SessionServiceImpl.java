package com.github.gabmldev.app.impl;

import com.github.gabmldev.app.entity.Session;
import com.github.gabmldev.app.repository.SessionRepository;
import com.github.gabmldev.app.services.SessionService;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.MissingClaimException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository repository;

    @Override
    public Optional<Map<String, Session>> findAllSessions(String userId) {
        return Optional.of(
            repository
                .findAllSessions(userId)
                .orElse(List.of())
                .stream()
                .collect(Collectors.toMap(Session::getJti, Function.identity()))
        );
    }

    @Override
    public Optional<Session> findSession(String userId, String jti) {
        return repository.findSession(userId, jti);
    }

    @Override
    public Session getCurrentSession(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public boolean verifySession(String userId, String jti)
        throws  MissingClaimException, IncorrectClaimException {
        Optional<Session> optSession = findSession(userId, jti);
        if (optSession.isEmpty()) {
            return false;
        }
        Session session = optSession.get();

        return !session.getExpiresAt().isBefore(LocalDateTime.now());
    }

    @Override
    public void deleteSession(String userId, String jti) {
        repository.deleteSession(userId, jti);
    }

    @Override
    public void deleteExpiredSession(String userId) {
        repository.deleteExpiredSession(userId);
    }

    @Override
    public void updateSession(
        String userId,
        String jti,
        String token,
        LocalDateTime eat
    ) {
        repository.updateSession(userId, jti, token, eat);
    }

    @Override
    public void saveSession(
        String jti,
        String token,
        String userId,
        LocalDateTime cat,
        LocalDateTime eat
    ) {
        repository.saveSession(jti, token, userId, cat, eat);
    }
}
