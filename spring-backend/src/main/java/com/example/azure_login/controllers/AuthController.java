package com.example.azure_login.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.http.HttpResponse;

@RestController
@Slf4j
public class AuthController {
    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_Admin')")
    public String Admin(Authentication authentication) {
        return "Admin message";
    }

    @GetMapping("/check")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> secureData() {
        // Retrieve the authenticated user's principal (JWT token)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        log.info("Authentication: {}", authentication);
        log.info("Jwt: {}", jwt);

        // Access user claims from the JWT token
        return ResponseEntity.ok("Authenticated user: " + jwt.getClaimAsString("username"));
    }
}

