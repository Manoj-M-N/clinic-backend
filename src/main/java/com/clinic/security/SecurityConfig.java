package com.clinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================================
                        // PUBLIC
                        // =========================================

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(
                                "/admin/login",
                                "/doctor/login"
                        ).permitAll()


                        // =========================================
                        // PATIENT (ADMIN ONLY)
                        // =========================================

                        .requestMatchers(HttpMethod.GET,
                                "/patient/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/patient/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/patient/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/patient/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
        "/medicine/**"
)
.hasAuthority("ROLE_ADMIN")


                        // =========================================
                        // DOCTOR FEATURES
                        // =========================================

                        .requestMatchers(HttpMethod.GET,
                                "/doctor/appointments/**")
                        .hasAuthority("ROLE_DOCTOR")

                        .requestMatchers(HttpMethod.GET,
                                "/doctor/appointments/history/**")
                        .hasAuthority("ROLE_DOCTOR")

                        .requestMatchers(HttpMethod.GET,
                                "/doctor/queue/**")
                        .hasAuthority("ROLE_DOCTOR")

                        .requestMatchers(HttpMethod.PUT,
                                "/doctor/queue/next/**")
                        .hasAuthority("ROLE_DOCTOR")

                        .requestMatchers(HttpMethod.PUT,
                                "/appointment/start/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/appointment/complete/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")


                        // =========================================
                        // CONSULTATION
                        // =========================================

                        .requestMatchers(HttpMethod.POST,
                                "/consultation/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/consultation/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/medical-history/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/medical-history/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/emr/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/doctor/**")
                        .hasAnyAuthority(
                                "ROLE_DOCTOR",
                                "ROLE_ADMIN"
                        )

                        // =========================================
                        // PRESCRIPTION
                        // =========================================

                        .requestMatchers(HttpMethod.POST,
                                "/prescription/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/prescription/**")
                        .hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")


                        // =========================================
                        // DOCTOR MANAGEMENT (ADMIN ONLY)
                        // =========================================

                        .requestMatchers(HttpMethod.GET,
                                "/doctor/all")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/doctor/{id}")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/doctor")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/doctor/{id}")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/doctor/{id}")
                        .hasRole("ADMIN")


                        // =========================================
                        // APPOINTMENT (ADMIN ONLY)
                        // =========================================

                        .requestMatchers(HttpMethod.GET,
                                "/appointment/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/appointment/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/appointment/cancel/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/appointment/**")
                        .hasRole("ADMIN")


                        // =========================================
                        // EVERYTHING ELSE
                        // =========================================

                        .anyRequest().authenticated()
                )

                // =========================================
                // JWT FILTER
                // =========================================

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // In your SecurityConfig.java file on the backend

@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // THIS IS THE CRUCIAL CHANGE
    // Instead of setAllowedOrigins, we use setAllowedOriginPatterns.
    // The "*" is a wildcard that will match any origin.
    // This is the most robust way to solve this specific issue.
    configuration.setAllowedOriginPatterns(List.of("*"));

    // The rest of the configuration remains the same
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    
    return source;
}
}