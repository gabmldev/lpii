package com.github.gabmldev.app.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.impl.AuthServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthServiceImpl authImpl;

    @GetMapping("/token")
    public void token() {
    }

    @PostMapping("/login")
    public void login(@RequestBody User user) {
    }

    @PostMapping("/logout")
    public void logout(@RequestBody User user) {
    }

    @PostMapping("/sign-up")
    public void create(@RequestBody User user) {
    }

    @PatchMapping("/restore-pwd")
    public void restorePwd(@RequestBody User user) {
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestBody User user) {
    }
}
