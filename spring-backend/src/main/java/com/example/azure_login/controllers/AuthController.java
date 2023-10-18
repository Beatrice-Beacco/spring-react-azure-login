package com.example.azure_login.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.http.HttpResponse;

@RestController
@Slf4j
public class AuthController {
    @GetMapping("check")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_Admin')")
    public String Admin(Authentication user) {
        log.info("User logged in: " + user.toString());
        return "Admin message";
    }

    //TODO: replace with post mapping
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout request {}", request);
        try {
            // Clear the security context
            SecurityContextHolder.getContext().setAuthentication(null);
            // Perform logout
            new SecurityContextLogoutHandler().logout(request, response, null);
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            log.error("Logout error", e);
            return ResponseEntity.badRequest().body("Logout error");
        }
    }
}

