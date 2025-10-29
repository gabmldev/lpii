package com.github.gabmldev.app.impl;

import com.github.gabmldev.app.repository.AuthRepository;
import com.github.gabmldev.app.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthRepository repository;

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

    @Override
    public void signUp() {

    }

    @Override
    public void restorePwd() {

    }

    @Override
    public void deleteUser() {

    }
}
