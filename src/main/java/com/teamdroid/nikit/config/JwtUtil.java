package com.teamdroid.nikit.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.teamdroid.nikit.entity.SessionToken;
import com.teamdroid.nikit.service.SessionTokenService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

/**
 * Utility class for handling JWT tokens.
 *
 * <p>This class provides methods to generate, validate, and invalidate JWT tokens. Token
 * management is delegated to the {@code SessionTokenService}.
 */
@Component
public class JwtUtil {

    @Getter
    @Setter
    @Value("${jwt.secret}")
    private String secretKey;

    @Getter
    @Setter
    @Value("${jwt.expiration.seconds}")
    private long tokenExpirationSeconds;

    @Getter
    @Setter
    private Algorithm algorithm;

    private final SessionTokenService tokenService;

    /**
     * Constructs a {@code JwtUtil} instance with the specified token service.
     *
     * @param tokenService The service responsible for managing session tokens.
     */
    public JwtUtil(SessionTokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Initializes the JWT signing algorithm after the secret key is injected.
     */
    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    /**
     * Generates a new JWT token for the given username.
     *
     * <p>The token includes a unique identifier and has an expiration time specified in the
     * application properties. The newly generated token replaces any previous token for the same
     * user in the session token service.
     *
     * @param username The username for which the token is generated.
     * @return A {@code SessionToken} object containing the token and its expiration date.
     */
    public SessionToken generateToken(String username) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(System.currentTimeMillis() + tokenExpirationSeconds * 1000);

        String token =
                JWT.create()
                        .withSubject(username)
                        .withClaim("tokenId", UUID.randomUUID().toString())
                        .withIssuedAt(issuedAt)
                        .withExpiresAt(expiresAt)
                        .sign(this.algorithm);

        SessionToken sessionToken = new SessionToken(token, expiresAt);
        tokenService.saveToken(username, sessionToken);

        return sessionToken;
    }

    /**
     * Validates the given JWT token.
     *
     * <p>This method checks if the token matches the most recent token for the user in the session
     * token service and verifies its signature and expiration.
     *
     * @param token The JWT token to validate.
     * @return {@code true} if the token is valid and matches the most recent token for the user;
     *     {@code false} otherwise.
     */
    public boolean validateToken(String token) {
        try {
            String username = extractUsername(token);
            SessionToken activeToken = tokenService.getToken(username);

            if (activeToken != null && activeToken.getToken().equals(token)) {
                JWT.require(this.algorithm).build().verify(token);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which the username is extracted.
     * @return The username contained in the token.
     */
    public String extractUsername(String token) {
        return JWT.require(this.algorithm).build().verify(token).getSubject();
    }

    /**
     * Invalidates the session token for the given username.
     *
     * <p>This method removes the user's session token from the session token service, effectively
     * invalidating it.
     *
     * @param username The username whose session token should be invalidated.
     */
    public void invalidateToken(String username) {
        tokenService.invalidateToken(username);
    }
}
