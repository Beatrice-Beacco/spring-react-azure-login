package com.example.azure_login.controllers;


import com.example.azure_login.models.BaseResponse;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @GetMapping("/check")
    public ResponseEntity<BaseResponse> secureData() {
        try {
            // Retrieve the authenticated user's principal (JWT token)
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            log.info("Authentication: {}", authentication.getPrincipal().toString());
            Jwt jwtToken = authentication.getToken();
            log.info("Jwt claims: {}", jwtToken.getClaims());
            // Access user claims from the JWT token
            BaseResponse response = BaseResponse.builder()
                    .data("Authenticated user: " + jwtToken.getClaimAsString("name"))
                    .statusCode(HttpServletResponse.SC_OK)
                    .success(true)
                    .build();
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            BaseResponse response = BaseResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

