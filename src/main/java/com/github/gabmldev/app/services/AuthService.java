package com.github.gabmldev.app.services;


public interface AuthService {
    public String getToken();
    public void login();
    public void logout();
    public void signUp();
    public void restorePwd();
    public void deleteUser();
}
