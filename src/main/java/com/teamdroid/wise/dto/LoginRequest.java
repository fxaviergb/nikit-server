package com.teamdroid.wise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Modelo para las credenciales de autenticación")
public class LoginRequest {

    @Schema(description = "Nombre de usuario", example = "admin", required = true)
    private String username;

    @Schema(description = "Contraseña del usuario", example = "password", required = true)
    private String password;
}