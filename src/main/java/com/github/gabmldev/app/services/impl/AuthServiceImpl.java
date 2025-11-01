package com.github.gabmldev.app.services.impl;

import java.time.LocalDateTime;
import java.util.*;

import com.github.gabmldev.app.utils.*;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.gabmldev.app.entity.Session;
import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.repository.AuthRepository;
import com.github.gabmldev.app.repository.RoleRepository;
import com.github.gabmldev.app.services.AuthService;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getToken(UUID userId) {
        return sessionService.getCurrentSession(userId).getToken();
    }

    @Override
    public Map<String, Object> login(LoginBody body) {
        try {

            User user = authRepository.findByEmail(body.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + body.getEmail()));

            boolean matches = passwordEncoder.matches(body.getPwd(), user.getPwd());

            if (!matches) {
                throw new NoResultException("Password not match");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPwd()));

            UserClaims claims = new UserClaims();

            LocalDateTime cat = LocalDateTime.now();
            LocalDateTime expiry = cat.plusHours(1);

            claims.setId(user.getId());
            claims.setUsername(user.getUsername());
            claims.setEmail(user.getEmail());
            claims.setRole(roleRepository.findNameById(user.getRole().getId()));
            claims.setCreatedAt(cat);
            claims.setExpiresAt(expiry);

            Map<String, Object> customClaims = new HashMap<>();
            customClaims.put("data", claims);

            String token = jwtService.generateToken(user.getUsername(), customClaims);

            String jti = jwtService.extractJti(token);

            sessionService.createSession(user.getId(), jti, token, cat, expiry);

            String msg = "Welcome back " + user.getUsername() + "!";

            return ResponseMap.builder()
                    .put("status", "authenticated")
                    .put("message", msg)
                    .put("token", token)
                    .build();
        } catch (Exception e) {
            if (e instanceof UsernameNotFoundException) {
                return ResponseMap.builder()
                        .put("status", "not-found")
                        .put("message", "There was trouble finding your username. If you don't have an account, please create one.")
                        .put("reason", e.getMessage())
                        .build();
            }

            if (e instanceof NoResultException) {
                return ResponseMap.builder()
                        .put("status", "error")
                        .put("message", "We obtained an unexpected result in the validation.")
                        .put("reason", e.getMessage())
                        .build();
            }

            return ResponseMap.builder()
                    .put("status", "server-error")
                    .put("message", "Something unexpected happened, please try again or try other data.")
                    .put("reason", "Unexpected event")
                    .build();
        }
    }

    @Override
    public Map<String, Object> logout(String token) {
        UserClaims userClaims = jwtService.extractUserClaims(token);
        String jti = jwtService.extractJti(token);
        Optional<Session> optSession = sessionService.findSession(
                userClaims.getId(),
                jti);
        optSession.ifPresent(session -> sessionService.deleteSession(userClaims.getId(), jti));

        return ResponseMap.builder()
                .put("status", "success")
                .put("message", "We hope to see you soon!")
                .build();
    }

    @Override
    public Map<String, Object> signUp(SignUpBody body) {
        Optional<User> uByUsername =  authRepository.findByUsername(body.getUsername());
        Optional<User> uByEmail =  authRepository.findByEmail(body.getEmail());

        if (
           uByUsername.isPresent() ||
           uByEmail.isPresent()
        ) {
            return ResponseMap.builder()
                    .put("status", "invalid")
                    .put("reason", "Data entered already exists, please check your email or username and change it.")
                    .put("errors", new SignUpErrors(uByUsername.isPresent(), uByEmail.isPresent()))
                    .build();
        }

        User newUser = new User();

        newUser.setId(UUID.randomUUID());
        newUser.setUsername(body.getUsername());
        newUser.setUsername(body.getEmail());
        newUser.setPwd(passwordEncoder.encode(body.getPwd()));

        authRepository.save(newUser);

        return ResponseMap.builder()
                .put("status", "success")
                .put("reason", "Your details are correct, your account has been created")
                .put("errors", null)
                .build();
    }

    @Override
    public void restorePwd() {
    }

    @Override
    public void deleteUser() {
    }
}
