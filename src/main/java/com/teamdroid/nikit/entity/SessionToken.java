package com.teamdroid.nikit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Representa un token de sesión, incluyendo el token JWT y su fecha de expiración.
 */
@Data
@AllArgsConstructor
public class SessionToken {
    private String token;
    private Date expiresAt;
}
