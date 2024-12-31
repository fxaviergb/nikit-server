package com.teamdroid.nikit.repository;

import com.teamdroid.nikit.entity.SessionToken;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio en memoria para manejar los tokens de sesión activos.
 */
@Repository
public class SessionTokenRepository {

    /**
     * Base de datos en memoria
     */
    private final Map<String, SessionToken> activeTokens = new ConcurrentHashMap<>();

    /**
     * Guarda o actualiza un token de sesión para un usuario.
     *
     * @param username    El nombre de usuario.
     * @param sessionToken El token de sesión asociado.
     */
    public void save(String username, SessionToken sessionToken) {
        activeTokens.put(username, sessionToken);
    }

    /**
     * Obtiene el token de sesión activo para un usuario.
     *
     * @param username El nombre de usuario.
     * @return El token de sesión activo, o null si no existe.
     */
    public SessionToken findByUsername(String username) {
        return activeTokens.get(username);
    }

    /**
     * Elimina el token de sesión para un usuario.
     *
     * @param username El nombre de usuario.
     */
    public void delete(String username) {
        activeTokens.remove(username);
    }
}
