package com.jerusalem.jerusalem_api.config;

import com.jerusalem.jerusalem_api.business.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("Processing request for URI: {}", request);

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No valid Bearer token found in Authorization header for URI: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        logger.info("Extracted JWT token from request");

        try {
            final String userEmail = jwtService.extractUsername(jwt);
            if (userEmail == null) {
                logger.warn("No username extracted from JWT token for URI: {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            logger.info("Extracted user email from JWT: {}", userEmail);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (userDetails == null) {
                    logger.error("User details not found for email: {} for URI: {}", userEmail, request.getRequestURI());
                    filterChain.doFilter(request, response);
                    return;
                }

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("JWT token validated and authentication set for user: {} for URI: {}",
                            userEmail, request.getRequestURI());
                } else {
                    logger.warn("JWT token invalid or expired for user: {} for URI: {}",
                            userEmail, request.getRequestURI());
                }
            } else {
                logger.info("Authentication already set in SecurityContext for user: {}", userEmail);
            }
        } catch (Exception e) {
            logger.error("Error processing JWT authentication for URI: {}. Error: {}",
                    request.getRequestURI(), e.getMessage(), e);
        }

        logger.info("Continuing filter chain for URI: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}