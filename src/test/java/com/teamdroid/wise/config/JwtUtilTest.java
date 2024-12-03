package com.teamdroid.wise.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        // Arrange
        String username = "testuser";

        // Act
        String token = jwtUtil.generateToken(username);

        // Assert
        assertNotNull(token, "Generated token should not be null");
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername, "Extracted username should match the original");
    }

    @Test
    public void testValidateToken_ValidToken() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Act
        boolean isValid = jwtUtil.validateToken(token);

        // Assert
        assertTrue(isValid, "Token should be valid");
    }

    @Test
    public void testValidateToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.string";

        // Act
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Assert
        assertFalse(isValid, "Invalid token should not be valid");
    }

    @Test
    public void testValidateToken_ExpiredToken() throws InterruptedException {
        // Arrange
        JwtUtil jwtUtil = new JwtUtil() {
            private final Algorithm algorithm = Algorithm.HMAC256("mysecretkey"); // Inicializar el algoritmo

            @Override
            public String generateToken(String username) {
                return JWT.create()
                        .withSubject(username)
                        .withIssuedAt(new Date())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000)) // Expira en 1 segundo
                        .sign(algorithm);
            }
        };

        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Esperar para que el token expire
        Thread.sleep(2000);

        // Act
        boolean isValid = jwtUtil.validateToken(token);

        // Assert
        assertFalse(isValid, "Expired token should not be valid");
    }


    @Test
    public void testExtractUsername() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Act
        String extractedUsername = jwtUtil.extractUsername(token);

        // Assert
        assertEquals(username, extractedUsername, "Extracted username should match the original");
    }

    @Test
    public void testInvalidateToken() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Act
        jwtUtil.invalidateToken(username);
        boolean isValid = jwtUtil.validateToken(token);

        // Assert
        assertFalse(isValid, "Token should be invalid after explicit invalidation");
    }

    @Test
    public void testGenerateToken_ReplacesOldToken() {
        // Arrange
        String username = "testuser";
        String oldToken = jwtUtil.generateToken(username);

        // Act
        String newToken = jwtUtil.generateToken(username);
        boolean isOldTokenValid = jwtUtil.validateToken(oldToken);
        boolean isNewTokenValid = jwtUtil.validateToken(newToken);

        // Assert
        assertFalse(isOldTokenValid, "Old token should be invalid after generating a new one");
        assertTrue(isNewTokenValid, "New token should be valid");
    }
}
