package com.teamdroid.nikit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 *
 * <p>This class configures the security rules for the application, including endpoint access
 * permissions, JWT filter integration, and password encoding.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * <p>This method sets up access control rules, disables CSRF for REST APIs, and adds a custom
     * JWT filter to validate tokens in incoming requests.
     *
     * @param http The {@code HttpSecurity} object used to configure security rules.
     * @param jwtFilter The custom JWT filter for validating authentication tokens.
     * @return A {@code SecurityFilterChain} object representing the configured filter chain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter)
            throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/v1/auth/login", // Public endpoint for login
                                        "/swagger-ui/**", // Swagger UI resources
                                        "/api-docs/**"    // OpenAPI documentation
                                ).permitAll() // Allow public access
                                .anyRequest().authenticated() // Protect all other endpoints
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for REST APIs
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    /**
     * Provides a password encoder bean for encoding and verifying passwords.
     *
     * <p>This method uses {@code BCryptPasswordEncoder} to securely hash passwords.
     *
     * @return A {@code PasswordEncoder} object for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
