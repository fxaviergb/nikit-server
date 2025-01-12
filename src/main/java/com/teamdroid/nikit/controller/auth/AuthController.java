package com.teamdroid.nikit.controller.auth;

import com.teamdroid.nikit.config.JwtUtil;
import com.teamdroid.nikit.dto.LoginRequest;
import com.teamdroid.nikit.dto.LoginResponse;
import com.teamdroid.nikit.entity.SessionToken;
import com.teamdroid.nikit.entity.User;
import com.teamdroid.nikit.service.session.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for handling authentication operations.
 *
 * <p>This controller provides endpoints for user authentication, including login functionality.
 * It integrates with the {@code JwtUtil} for token generation and the {@code UserService} for
 * user authentication.
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired private JwtUtil jwtUtil;

    @Autowired private UserService userService;

    /**
     * Authenticates a user and generates a JWT token.
     *
     * <p>This endpoint validates the user's credentials. If the credentials are valid, it generates
     * a JWT token and returns it along with the token's expiration date. Otherwise, it returns a 401
     * Unauthorized response.
     *
     * @param credentials The login request containing the username and password.
     * @return A {@code ResponseEntity} containing a {@code LoginResponse} with the token and
     *     expiration date if successful, or an error message if authentication fails.
     */
    @Operation(
            summary = "User authentication",
            description =
                    "This endpoint authenticates a user using their credentials. If the credentials "
                            + "are valid, a JWT token is returned."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = LoginResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials",
                            content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "text/plain")})
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        Optional<User> authenticatedUser = userService.authenticate(username, password);

        if (authenticatedUser.isPresent()) {
            SessionToken sessionToken = jwtUtil.generateToken(username);
            return ResponseEntity.ok(
                    new LoginResponse(sessionToken.getToken(), sessionToken.getExpiresAt()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
