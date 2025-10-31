package com.github.gabmldev.app.impl;

import com.github.gabmldev.app.entity.Session;
import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.entity.UserClaims;
import com.github.gabmldev.app.repository.AuthRepository;
import com.github.gabmldev.app.repository.RoleRepository;
import com.github.gabmldev.app.services.AuthService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String getToken(String userId) {
        return sessionService.getCurrentSession(userId).getToken();
    }

    @Override
    public String login(String username, String pwd) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, pwd)
        );
        User user = authRepository.findByName(username);
        UserClaims claims = new UserClaims();

        LocalDateTime cat = LocalDateTime.now();
        LocalDateTime expiry = cat.plusHours(1);

        claims.setId(user.getId());
        claims.setUsername(user.getUsername());
        claims.setEmail(user.getEmail());
        claims.setRole(roleRepository.findRoleNameById(user.getRole().toString()));
        claims.setCreatedAt(cat);
        claims.setExpiresAt(expiry);

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("data", claims);

        String[] roles = roleRepository.findAllNames();

        String token = jwtService.generateToken(username, customClaims);

        String jti = jwtService.extractJti(token);

        sessionService.saveSession(jti, token, user.getId(), cat, expiry);
        return token;
    }

    @Override
    public void logout(String token) {
        UserClaims userClaims = jwtService.extractUserClaims(token);
        String jti = jwtService.extractJti(token);
        Optional<Session> optSession = sessionService.findSession(
            userClaims.getId(),
            jti
        );
        optSession.ifPresent(session ->
            sessionService.deleteSession(userClaims.getId(), jti)
        );
    }

    @Override
    public void signUp() {}

    @Override
    public void restorePwd() {}

    @Override
    public void deleteUser() {}
}
