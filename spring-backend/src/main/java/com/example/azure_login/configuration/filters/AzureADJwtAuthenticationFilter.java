package com.example.azure_login.configuration.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AzureADJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public AzureADJwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request); // Implement token extraction from the request headers
        log.info("Token in filter chain: {}", token);
        if (token == null) {
            setUnauthorizedResponse(response, "No token found in request");
            return;
        }
        try {
            Jwt jwt = getDecodedToken(token);
            log.info("Jwt in filter chain: {}", jwt);

            JwtAuthenticationToken auth = new JwtAuthenticationToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            setForbiddenResponse(response, "Invalid token: " + e.getMessage());
        }
    }

    Jwt getDecodedToken(String token) {
            return jwtDecoder.decode(token);
    }

    void setUnauthorizedResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            log.error("Error writing response: {}", e.getMessage());
        }
    }

    void setForbiddenResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            log.error("Error writing response: {}", e.getMessage());
        }
    }

}

