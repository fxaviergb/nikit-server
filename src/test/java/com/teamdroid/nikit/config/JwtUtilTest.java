package com.teamdroid.nikit.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.teamdroid.nikit.entity.SessionToken;
import com.teamdroid.nikit.service.session.SessionTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JwtUtilTest {

    @Mock private SessionTokenService tokenService;

    @InjectMocks private JwtUtil jwtUtil;

    private static final String SECRET_KEY = "mysecretkey";
    private static final long EXPIRATION_SECONDS = 1800; // 30 minutes
    private static final String USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inject mock values for @Value fields
        jwtUtil = new JwtUtil(tokenService);
        jwtUtil.setSecretKey(SECRET_KEY);
        jwtUtil.setTokenExpirationSeconds(EXPIRATION_SECONDS);
        jwtUtil.init(); // Manually call @PostConstruct method to initialize the algorithm
    }

    @Test
    void testGenerateToken() {
        // Act
        SessionToken sessionToken = jwtUtil.generateToken(USERNAME);

        // Assert
        assertNotNull(sessionToken, "SessionToken should not be null");
        assertNotNull(sessionToken.getToken(), "Token should not be null");
        assertNotNull(sessionToken.getExpiresAt(), "Expiration date should not be null");
        verify(tokenService, times(1)).saveToken(eq(USERNAME), any(SessionToken.class));
    }

    @Test
    void testValidateToken_ValidToken() {
        // Arrange
        SessionToken sessionToken = jwtUtil.generateToken(USERNAME);
        when(tokenService.getToken(USERNAME)).thenReturn(sessionToken);

        // Act
        boolean isValid = jwtUtil.validateToken(sessionToken.getToken());

        // Assert
        assertTrue(isValid, "Valid token should return true");
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.string";
        when(tokenService.getToken(USERNAME)).thenReturn(null);

        // Act
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Assert
        assertFalse(isValid, "Invalid token should return false");
    }

    @Test
    void testValidateToken_ExpiredToken() throws InterruptedException {
        // Arrange
        jwtUtil.setTokenExpirationSeconds(1); // Set short expiration for the test
        jwtUtil.init();
        SessionToken sessionToken = jwtUtil.generateToken(USERNAME);

        // Simulate token expiration
        Thread.sleep(2000);
        when(tokenService.getToken(USERNAME)).thenReturn(sessionToken);

        // Act
        boolean isValid = jwtUtil.validateToken(sessionToken.getToken());

        // Assert
        assertFalse(isValid, "Expired token should return false");
    }

    @Test
    void testExtractUsername() {
        // Arrange
        SessionToken sessionToken = jwtUtil.generateToken(USERNAME);

        // Act
        String extractedUsername = jwtUtil.extractUsername(sessionToken.getToken());

        // Assert
        assertEquals(USERNAME, extractedUsername, "Extracted username should match the original");
    }

    @Test
    void testInvalidateToken() {
        // Act
        jwtUtil.invalidateToken(USERNAME);

        // Assert
        verify(tokenService, times(1)).invalidateToken(USERNAME);
    }
}
