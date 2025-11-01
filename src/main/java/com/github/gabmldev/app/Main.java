package com.github.gabmldev.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.github.gabmldev.app.entity")
@ComponentScan({ "controller", "services", "config" })
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
