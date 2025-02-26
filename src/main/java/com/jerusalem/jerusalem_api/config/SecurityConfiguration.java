package com.jerusalem.jerusalem_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        logger.debug("SecurityConfiguration initialized with JwtAuthenticationFilter and AuthenticationProvider");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Spring Security filter chain");
        try {
            http
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                    .authorizeHttpRequests(auth -> {
                        logger.debug("Setting up request matchers: permitting /auth/**, requiring authentication for all else");
                        auth.requestMatchers("/auth/**").permitAll() // Allow unauthenticated access to /auth/**
                                .anyRequest().authenticated(); // Require authentication for all other requests
                    })
                    .sessionManagement(session -> {
                        logger.debug("Configuring stateless session management");
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Use stateless sessions
                    })
                    .authenticationProvider(authenticationProvider) // Set the authentication provider
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

            SecurityFilterChain chain = http.build();
            logger.info("Security filter chain configured successfully");
            return chain;
        } catch (Exception e) {
            logger.error("Failed to configure security filter chain. Error: {}", e.getMessage(), e);
            throw e; // Rethrow to fail application startup if configuration fails
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        logger.info("Configuring CORS settings");
        try {
            CorsConfiguration configuration = new CorsConfiguration();

            // Allow requests from http://localhost:8005
            configuration.setAllowedOrigins(List.of("http://localhost:8005"));
            // Allow GET and POST methods
            configuration.setAllowedMethods(List.of("GET", "POST"));
            // Allow Authorization and Content-Type headers
            configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            // Apply CORS configuration to all endpoints
            source.registerCorsConfiguration("/**", configuration);

            logger.debug("CORS configured: allowed origins={}, methods={}, headers={}",
                    configuration.getAllowedOrigins(),
                    configuration.getAllowedMethods(),
                    configuration.getAllowedHeaders());
            return source;
        } catch (Exception e) {
            logger.error("Failed to configure CORS settings. Error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to configure CORS", e); // Wrap and rethrow for consistency
        }
    }
}