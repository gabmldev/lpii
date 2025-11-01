package com.github.gabmldev.app.controller;

import com.github.gabmldev.app.utils.LoginBody;
import com.github.gabmldev.app.utils.SignUpBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.services.impl.AuthServiceImpl;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthServiceImpl authImpl;

    @GetMapping("/token")
    public void token() {
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginBody body) {
        return authImpl.login(body);
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestBody String body) {
        return authImpl.logout(body);
    }

    @PostMapping("/sign-up")
    public Map<String, Object> create(@RequestBody SignUpBody body) {
        return authImpl.signUp(body);
    }

    @PatchMapping("/restore-pwd")
    public void restorePwd(@RequestBody User user) {
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestBody User user) {
    }
}
