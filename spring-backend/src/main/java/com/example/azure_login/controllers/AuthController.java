package com.example.azure_login.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    JwtDecoder jwtDecoder;

    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_Admin')")
    public String Admin(Authentication authentication) {
        return "Admin message";
    }

    @GetMapping("/check")
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> secureData() {
        try {
            // Retrieve the authenticated user's principal (JWT token)
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            log.info("Authentication: {}", authentication.getPrincipal().toString());
            Jwt jwtToken = authentication.getToken();
            log.info("Jwt: {}", jwtToken);
            log.info("Jwt claims: {}", jwtToken.getClaims());
            // Access user claims from the JWT token
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("Authenticated user: "/* + jwt.getClaimAsString("username")*/);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

