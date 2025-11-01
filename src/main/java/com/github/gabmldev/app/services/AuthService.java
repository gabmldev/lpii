package com.github.gabmldev.app.services;

import com.github.gabmldev.app.utils.LoginBody;
import com.github.gabmldev.app.utils.SignUpBody;

import java.util.Map;
import java.util.UUID;

public interface AuthService {
    public String getToken(UUID userId);

    public Map<String, Object> login(LoginBody body);

    public Map<String, Object> logout(String token);

    public Map<String, Object> signUp(SignUpBody body);

    public void restorePwd();

    public void deleteUser();
}
