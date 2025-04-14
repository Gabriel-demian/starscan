package com.galaxydata.starscan.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);
    }

    @Test
    public void testExtractUsernameFromValidToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testValidateValidToken() {
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    public void testValidateInvalidToken() {
        String invalidToken = "invalid.token";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    public void testValidateExpiredToken() throws InterruptedException {
        // Generate a token with a very short expiration (e.g., 1 second)
        JwtUtil shortLivedJwtUtil = new JwtUtil() {
            @Override
            public String generateToken(String username) {
                return super.generateToken(username).replace(
                        "exp=" + (System.currentTimeMillis() + 1000 * 60 * 60 * 10) / 1000,
                        "exp=" + (System.currentTimeMillis() + 1000) / 1000);
            }
        };

        String expiredToken = shortLivedJwtUtil.generateToken("testuser");
        Thread.sleep(2000); // Wait for the token to expire
        assertFalse(jwtUtil.validateToken(expiredToken));

    }

    @Test
    public void testExtractUsernameFromMalformedToken(){
        String malformedToken = "malformed.token";
        assertThrows(MalformedJwtException.class, ()-> jwtUtil.extractUsername(malformedToken));
    }
}