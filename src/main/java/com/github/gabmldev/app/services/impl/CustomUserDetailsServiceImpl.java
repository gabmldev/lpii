package com.github.gabmldev.app.services.impl;

import com.github.gabmldev.app.entity.User;
import com.github.gabmldev.app.repository.AuthRepository;
import com.github.gabmldev.app.repository.RoleRepository;
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
        User user = authRepository
            .findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + username)
            );
        String role = roleRepository.findNameById(user.getRole().getId());

        // convierte tu entidad User a
        // org.springframework.security.core.userdetails.User
        return org.springframework.security.core.userdetails.User.withUsername(
            user.getUsername()
        )
            .password(user.getPwd()) // ya debe ser bcrypt
            .authorities(role)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }
}
