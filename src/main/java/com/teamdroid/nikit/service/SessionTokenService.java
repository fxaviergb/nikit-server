package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.SessionToken;
import com.teamdroid.nikit.repository.SessionTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para manejar la lógica relacionada con los tokens de sesión.
 */
@Service
public class SessionTokenService {

    @Autowired
    private SessionTokenRepository repository;

    /**
     * Guarda o actualiza un token de sesión.
     *
     * @param username    El nombre de usuario.
     * @param sessionToken El token de sesión asociado.
     */
    public void saveToken(String username, SessionToken sessionToken) {
        repository.save(username, sessionToken);
    }

    /**
     * Obtiene un token de sesión activo para un usuario.
     *
     * @param username El nombre de usuario.
     * @return El token de sesión activo, o null si no existe.
     */
    public SessionToken getToken(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Elimina un token de sesión.
     *
     * @param username El nombre de usuario.
     */
    public void invalidateToken(String username) {
        repository.delete(username);
    }
}
