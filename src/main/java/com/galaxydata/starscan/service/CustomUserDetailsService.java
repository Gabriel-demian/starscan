package com.galaxydata.starscan.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Replace this with your actual user lookup logic
        if ("testUser".equals(username)) {
            // Encode the password using BCrypt
            String encodedPassword = passwordEncoder.encode("testPassword");
            return new User("testUser", encodedPassword, Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}