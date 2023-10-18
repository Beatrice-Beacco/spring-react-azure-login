package com.example.azure_login.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Value("${spring.security.oauth2.client.provider.azure.jwk-uri}")
    String jwkUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/public/**").permitAll()
                                .anyRequest().authenticated()
                ).oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(
                                        userInfo -> userInfo
                                                .oidcUserService(oidcUserService())
                                )
                );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(jwkUri)
                .build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new CustomOidcUserService();
    }
}

