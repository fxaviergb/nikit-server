package com.teamdroid.wise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v1/auth/login",
                                "/swagger-ui/**",   // Swagger UI assets
                                "/swagger-ui.html", // (Por compatibilidad con algunas versiones)
                                "/v3/api-docs/**",  // OpenAPI documentation
                                "/v3/api-docs.yaml" // YAML documentation (si aplica)
                        ).permitAll() // Permitir acceso público
                        .anyRequest().authenticated() // Proteger otros endpoints
                )
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para la API REST
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Agregar JwtFilter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

