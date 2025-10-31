package com.github.gabmldev.app.services;

public interface AuthService {
    public String getToken(String userId);
    public String login(String username, String pwd);
    public void logout(String token);
    public void signUp();
    public void restorePwd();
    public void deleteUser();
}
