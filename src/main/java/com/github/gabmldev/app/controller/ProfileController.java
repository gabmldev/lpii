package com.github.gabmldev.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workshop/profile")
public class ProfileController {
    @GetMapping()
    public void getProfileData() {

    }

    @GetMapping(value = "/{username}")
    public void getPublicProfileData(@PathVariable String username) {};
}
