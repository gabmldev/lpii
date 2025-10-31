package com.github.gabmldev.app.security;

import com.github.gabmldev.app.entity.UserClaims;
import com.github.gabmldev.app.impl.CustomUserDetailsServiceImpl;
import com.github.gabmldev.app.impl.JwtServiceImpl;
import com.github.gabmldev.app.impl.SessionServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private SessionServiceImpl sessionService;

    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtServiceImpl jwtService;

    public JwtAuthFilter(
        JwtServiceImpl jwtService,
        CustomUserDetailsServiceImpl userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            UserClaims userClaims = jwtService.extractUserClaims(token);
            String jti = jwtService.extractJti(token);

            try {
                if (!sessionService.verifySession(userClaims.getId(), jti)) {
                    throw new Exception("Error in verify session");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("Content-Type: application/json");
                response.getWriter().write("{\"error\":\"Invalid session\"}");
                return;
            }

            if (
                username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                    username
                );
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
