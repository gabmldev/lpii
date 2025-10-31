package com.github.gabmldev.app.impl;

import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.repository.AuthRepository;
import com.github.gabmldev.app.repository.RoleRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User u = authRepository.findByName(username);
        String[] roles = roleRepository.findAllNames();

        if (Objects.isNull(u)) throw new UsernameNotFoundException(
            "User not found: " + username
        );

        // convierte tu entidad User a
        // org.springframework.security.core.userdetails.User
        return org.springframework.security.core.userdetails.User.withUsername(
            u.getUsername()
        )
            .password(u.getPwd()) // ya debe ser bcrypt
            .authorities(roles)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }
}
