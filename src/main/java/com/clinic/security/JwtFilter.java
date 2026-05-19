package com.clinic.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod()) ||
               request.getRequestURI().startsWith("/admin/login") ||
               request.getRequestURI().startsWith("/doctor/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 🔍 DEBUG LOG
        System.out.println("------------------------------------------------");
        System.out.println("Incoming Request: " + request.getRequestURI());
        System.out.println("Auth Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            System.out.println("Token extracted: " + token);

            try {
                if (jwtUtil.validateToken(token)) {

                    String username = jwtUtil.extractUsername(token);
                    String role = jwtUtil.extractRole(token);

                    System.out.println("JWT Username: " + username);
                    System.out.println("JWT Raw Role: " + role);

                    // ✅ FIX: Handle NULL ROLE safely
                    if (role != null && !role.isEmpty()) {

                        String formattedRole = role.startsWith("ROLE_")
                                ? role
                                : "ROLE_" + role;

                        System.out.println("Final Authority assigned: " + formattedRole);

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        List.of(new SimpleGrantedAuthority(formattedRole))
                                );

                        // ✅ Attach request details
                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        // ✅ Set authentication
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } else {
                        System.out.println("❌ ROLE is NULL or EMPTY in token");
                    }

                } else {
                    System.out.println("❌ Token Validation Failed!");
                }

            } catch (Exception e) {
                System.out.println("❌ Exception in JWT Filter: " + e.getMessage());
            }

        } else {
            System.out.println("❌ No Bearer Token found in Header");
        }

        System.out.println("------------------------------------------------");

        filterChain.doFilter(request, response);
    }
}