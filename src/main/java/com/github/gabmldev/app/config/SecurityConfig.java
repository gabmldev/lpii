package com.github.gabmldev.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.github.gabmldev.app.services.impl.CustomUserDetailsServiceImpl;
import com.github.gabmldev.app.security.JwtAuthFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String cspFlags = "default-src 'self'; script-src 'self' 'nonce-PLACEHOLDER'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:;";

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter) throws Exception {
        AuthenticationEntryPoint restEntryPoint = (
                request,
                response,
                authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Unauthorized\"}");
        };

        http
                .headers(h -> h
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                        .xssProtection(x -> x.disable()))
                .csrf(AbstractHttpConfigurer::disable) // desactivar CSRF para API REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(e -> e.authenticationEntryPoint(restEntryPoint))
                .passwordManagement(m -> m.changePasswordPage("/auth/restore-pwd"))
                .formLogin(fl ->
                    fl.loginPage("/auth/login")
                      .loginProcessingUrl("/api/auth/login")
                      .defaultSuccessUrl("/workshop/profile", true)
                      .failureUrl("/login?status='error'&errors=")
                      .permitAll()
                )
                .logout(l ->
                    l.logoutUrl("/auth/logout")
                     .logoutSuccessUrl("/auth/logout?status=successful")
                     .invalidateHttpSession(true)
                     .permitAll()
                )
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/**")
                                .hasAnyRole()
                                .anyRequest()
                                .permitAll()
                                .requestMatchers("/workshop/admin/**")
                                .hasRole("admin")
                                .anyRequest()
                                .permitAll()
                )
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedOrigins));
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
                userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
