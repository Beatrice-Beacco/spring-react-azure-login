package com.example.azure_login.configuration.filters;

import com.example.azure_login.utils.ServletResponseUtils;
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
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AzureADJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public AzureADJwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> token = extractToken(request);
            log.info("Token in filter chain: {}", token);
            if (token.isEmpty()) {
                ServletResponseUtils.setUnauthorizedResponse(response, "No token found in request");
                return;
            }
            Jwt jwt = getDecodedToken(token.get());
            log.info("Jwt in filter chain: {}", jwt);
            JwtAuthenticationToken auth = new JwtAuthenticationToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("Error: {}", e.getMessage());
            ServletResponseUtils.setForbiddenResponse(response, "Invalid token: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            ServletResponseUtils.setInternalServerErrorResponse(response, "Internal server error: " + e.getMessage());
        }
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }

        return Optional.empty();
    }
    Jwt getDecodedToken(String token) throws JwtException {
            return jwtDecoder.decode(token);
    }
}

