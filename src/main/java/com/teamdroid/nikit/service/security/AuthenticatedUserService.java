package com.teamdroid.nikit.service.security;

import com.teamdroid.nikit.logging.TDLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticatedUserService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticatedUserService.class);

    /**
     * Obtiene el userId (username) del usuario autenticado en el contexto de seguridad actual.
     * Si no hay usuario autenticado, retorna usuario generico.
     *
     * @return userId extraído del JWT o usuario generico si no se encuentra autenticación.
     */
    public String getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String genericUser = "system";
        if (auth == null || !auth.isAuthenticated()) {
            TDLogger.log(logger, TDLogger.Level.WARN, "Sin autenticación válida en SecurityContext.");
            return genericUser;
        }

        if (auth.getPrincipal() instanceof User user) {
            String userId = user.getUsername();
            TDLogger.logF(logger, TDLogger.Level.INFO, "Usuario autenticado: {}", userId);
            return userId;
        }

        TDLogger.logF(logger, TDLogger.Level.WARN, "Principal no reconocido: {}", auth.getPrincipal());
        return genericUser;
    }
}
