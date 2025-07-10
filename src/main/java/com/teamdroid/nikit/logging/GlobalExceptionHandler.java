package com.teamdroid.nikit.logging;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream().map(e -> Map.of(
                "field", e.getField(),
                "message", e.getDefaultMessage()
        )).toList();

        TDLogger.log(logger, TDLogger.Level.WARN, new StructuredLogEvent(
                "ERR", req.getMethod(), req.getRequestURI(), "GlobalExceptionHandler", "handleValidation", errors
        ));

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", 400,
                "error", "Validation Failed",
                "fieldErrors", errors,
                "path", req.getRequestURI(),
                "requestId", MDC.get("requestId")
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        TDLogger.log(logger, TDLogger.Level.WARN, new StructuredLogEvent(
                "ERR", req.getMethod(), req.getRequestURI(), "GlobalExceptionHandler", "handleUnreadable", "Malformed JSON"
        ));

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", 400,
                "error", "Malformed JSON",
                "message", "Request body is invalid or unreadable",
                "path", req.getRequestURI(),
                "requestId", MDC.get("requestId")
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, HttpServletRequest req) {
        TDLogger.log(logger, TDLogger.Level.ERROR, new StructuredLogEvent(
                "ERR", req.getMethod(), req.getRequestURI(), "GlobalExceptionHandler", "handleGeneric", ex.getMessage()
        ));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", 500,
                "error", "Internal Server Error",
                "message", ex.getMessage(),
                "path", req.getRequestURI(),
                "requestId", MDC.get("requestId")
        ));
    }
}
