package com.teamdroid.wise.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT authentication filter for validating tokens in HTTP requests.
 *
 * <p>This filter processes each incoming request to validate the JWT token present in the
 * Authorization header. If the token is valid, it sets up the security context for Spring Security
 * to authenticate the user during the request processing.</p>
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired private JwtUtil jwtUtil;

    /**
     * Main method of the filter that processes each incoming HTTP request.
     *
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param filterChain The filter chain that processes the request.
     * @throws ServletException If an error occurs during processing.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTH_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(BEARER_PREFIX.length());

            if (jwtUtil.validateToken(token)) {
                setAuthenticationContext(request, token);
            } else {
                handleInvalidToken(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Sets up the Spring Security context with the authenticated user.
     *
     * @param request The HTTP request.
     * @param token The valid JWT token.
     */
    private void setAuthenticationContext(HttpServletRequest request, String token) {
        String username = jwtUtil.extractUsername(token);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        new User(username, "", Collections.emptyList()), // Authenticated user
                        null, // No credentials required here
                        Collections.emptyList() // User roles or permissions
                );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * Responds with an HTTP 401 (Unauthorized) error when the JWT token is invalid.
     *
     * @param response The HTTP response.
     * @throws IOException If an I/O error occurs while writing the response.
     */
    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid or expired JWT token");
    }
}
