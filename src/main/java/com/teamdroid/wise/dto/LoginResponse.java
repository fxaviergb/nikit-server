package com.teamdroid.wise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Modelo para la respuesta de autenticación.
 * <p>
 * Incluye el token JWT generado y su fecha de caducidad.
 */
@Data
@AllArgsConstructor
@Schema(description = "Modelo para la respuesta de autenticación")
public class LoginResponse {

    @Schema(
            description = "Token JWT generado tras la autenticación",
            example = "eyJhbGciOiJIUzI1..."
    )
    private String token;

    @Schema(
            description = "Fecha de caducidad del token JWT",
            example = "2024-12-04T15:30:00.000+0000"
    )
    private Date expiresAt;
}
