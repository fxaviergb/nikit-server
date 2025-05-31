package com.teamdroid.nikit.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.helpers.MessageFormatter;

/**
 * TDLogger is a structured logging utility that outputs logs in JSON format.
 * It wraps SLF4J's Logger and enriches logs with metadata using StructuredLogEvent.
 * Useful for consistent observability across microservices when used with ELK or similar stacks.
 */
public class TDLogger {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Supported log levels for structured logging.
     */
    public enum Level {
        INFO, DEBUG, WARN, ERROR, TRACE
    }

    /**
     * Logs a fully constructed StructuredLogEvent.
     *
     * @param logger SLF4J logger instance
     * @param level  Logging level to be used
     * @param event  Structured log event to serialize and emit
     */
    public static void log(Logger logger, Level level, StructuredLogEvent event) {
        if (!isLevelEnabled(logger, level)) return;
        writeLog(logger, level, event);
    }

    /**
     * Logs a message and payload objects, serializing them into JSON and wrapping them in a StructuredLogEvent.
     *
     * @param logger  SLF4J logger instance
     * @param level   Logging level to be used
     * @param message Descriptive message (used as `message` in structured log)
     * @param objects Optional payload objects to include in the log
     */
    public static void log(Logger logger, Level level, String message, Object... objects) {
        if (!isLevelEnabled(logger, level)) return;

        try {
            Object[] jsonValues = new Object[objects.length];
            for (int i = 0; i < objects.length; i++) {
                jsonValues[i] = objectMapper.writeValueAsString(objects[i]);
            }

            StructuredLogEvent event = StructuredLogEvent.buildGeneric(logger, message, jsonValues);
            writeLog(logger, level, event);
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize object to JSON: {}", e.getMessage());
        }
    }

    /**
     * Logs a simple message without any payload.
     *
     * @param logger  SLF4J logger instance
     * @param level   Logging level to be used
     * @param message Descriptive message to log
     */
    public static void log(Logger logger, Level level, String message) {
        if (!isLevelEnabled(logger, level)) return;

        StructuredLogEvent event = StructuredLogEvent.buildGeneric(logger, message, null);
        writeLog(logger, level, event);
    }

    /**
     * Logs a formatted message with SLF4J-style placeholders (e.g., "ID: {}").
     * It replaces the placeholders and uses structured logging internally.
     *
     * @param logger  SLF4J logger instance
     * @param level   Logging level
     * @param message Message template with {} placeholders
     * @param args    Arguments to fill in the placeholders
     */
    public static void logF(Logger logger, Level level, String message, Object... args) {
        if (!isLevelEnabled(logger, level)) return;

        String formattedMessage = MessageFormatter.arrayFormat(message, args).getMessage();
        log(logger, level, formattedMessage);
    }

    /**
     * Serializes the StructuredLogEvent and writes it using the appropriate log level.
     * Also includes requestId from MDC if available.
     *
     * @param logger SLF4J logger instance
     * @param level  Logging level
     * @param event  Structured event to log
     */
    private static void writeLog(Logger logger, Level level, StructuredLogEvent event) {
        try {
            String requestId = MDC.get("requestId");
            String json = objectMapper.writeValueAsString(event);

            String enrichedJson = (requestId != null && !requestId.isEmpty())
                    ? String.format("{\"requestId\":\"%s\",%s", requestId, json.substring(1))
                    : json;

            switch (level) {
                case INFO -> logger.info(enrichedJson);
                case DEBUG -> logger.debug(enrichedJson);
                case WARN -> logger.warn(enrichedJson);
                case ERROR -> logger.error(enrichedJson);
                case TRACE -> logger.trace(enrichedJson);
            }
        } catch (JsonProcessingException e) {
            logger.warn("[TDLogger] Failed to serialize structured log: {}", e.getMessage());
        }
    }

    /**
     * Checks if the specified log level is enabled for the given logger.
     *
     * @param logger SLF4J logger instance
     * @param level  Logging level to check
     * @return true if the level is enabled, false otherwise
     */
    private static boolean isLevelEnabled(Logger logger, Level level) {
        return switch (level) {
            case INFO -> logger.isInfoEnabled();
            case DEBUG -> logger.isDebugEnabled();
            case WARN -> logger.isWarnEnabled();
            case ERROR -> logger.isErrorEnabled();
            case TRACE -> logger.isTraceEnabled();
        };
    }
}
