package com.teamdroid.nikit.logging;

import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

/**
 * Utilidad para obtener o generar el requestId actual de MDC,
 * útil para usarlo fuera del contexto HTTP (tareas, hilos, servicios).
 */
public class RequestIdGenerator {

    private static final String MDC_REQUEST_ID_KEY = "requestId";

    /**
     * Obtiene el requestId actual desde MDC o genera uno nuevo si no existe.
     */
    public static String getOrGenerate() {
        return Optional.ofNullable(MDC.get(MDC_REQUEST_ID_KEY))
                .orElseGet(() -> {
                    String generated = UUID.randomUUID().toString();
                    MDC.put(MDC_REQUEST_ID_KEY, generated);
                    return generated;
                });
    }

    /**
     * Establece manualmente un requestId en el contexto actual.
     */
    public static void set(String requestId) {
        MDC.put(MDC_REQUEST_ID_KEY, requestId);
    }

    /**
     * Limpia el requestId del contexto.
     */
    public static void clear() {
        MDC.remove(MDC_REQUEST_ID_KEY);
    }
}