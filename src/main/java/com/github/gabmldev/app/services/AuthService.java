package com.github.gabmldev.app.services;

import java.util.UUID;

public interface AuthService {
    public String getToken(UUID userId);

    public String login(String username, String pwd);

    public void logout(String token);

    public void signUp();

    public void restorePwd();

    public void deleteUser();
}
