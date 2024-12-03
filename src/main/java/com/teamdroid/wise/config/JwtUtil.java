package com.teamdroid.wise.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mysecretkey";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    // Almacenamiento en memoria para tokens activos
    private final ConcurrentHashMap<String, String> activeTokens = new ConcurrentHashMap<>();

    // Generar un token único y reemplazar el anterior
    public String generateToken(String username) {
        String newToken = JWT.create()
                .withSubject(username)
                .withClaim("tokenId", UUID.randomUUID().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutos
                .sign(algorithm);

        // Guardar el nuevo token en el almacenamiento y reemplazar el anterior
        activeTokens.put(username, newToken);
        return newToken;
    }

    // Validar el token y asegurarse de que es el más reciente
    public boolean validateToken(String token) {
        try {
            String username = extractUsername(token);
            String activeToken = activeTokens.get(username);

            if (activeToken != null && activeToken.equals(token)) {
                JWT.require(algorithm).build().verify(token); // Verificar firma y expiración
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    // Extraer el username del token
    public String extractUsername(String token) {
        return JWT.require(algorithm).build().verify(token).getSubject();
    }

    // Invalidar un token explícitamente
    public void invalidateToken(String username) {
        activeTokens.remove(username);
    }
}
