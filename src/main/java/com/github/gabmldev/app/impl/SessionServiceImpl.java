package com.github.gabmldev.app.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.gabmldev.app.entity.Session;
import com.github.gabmldev.app.repository.SessionRepository;
import com.github.gabmldev.app.services.SessionService;

import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.MissingClaimException;

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
                        .collect(Collectors.toMap(Session::getJti, Function.identity())));
    }

    @Override
    public Optional<Session> findSession(String userId, String jti) {
        return repository.findSession(userId, jti);
    }

    /**
     * @throws InvalidClaimException   Excepción que indica que una reclamación
     *                                 analizada es inválida de algún modo. Las
     *                                 subclases reflejan el motivo específico por
     *                                 el que la reclamación es inválida.
     * @throws MissingClaimException   Se lanza una excepción al descubrir que no
     *                                 existe un reclamo requerido, lo que indica
     *                                 que el JWT no es válido y no se puede
     *                                 utilizar.
     * @throws IncorrectClaimException Se lanza una excepción al descubrir que un
     *                                 reclamo requerido no es igual al valor
     *                                 requerido, lo que indica que el JWT no es
     *                                 válido y no se puede utilizar.
     */
    @Override
    public void verifySession() {
    }

    @Override
    public void deleteSession() {
    }

    @Override
    public void updateSession() {
    }

    @Override
    public void saveSession() {
    }

    @Override
    public void createSession() {
    }
}
