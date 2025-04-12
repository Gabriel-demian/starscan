package com.galaxydata.starscan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/people/**").permitAll() // Allow public access to /planets
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for simplicity (not recommended for production)

        return http.build();
    }


}