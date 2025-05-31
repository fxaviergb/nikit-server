package com.teamdroid.nikit.logging;

import lombok.*;
import org.slf4j.Logger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StructuredLogEvent {

    private String stage; // IN, OUT, ERR, MSG
    private String httpMethod;
    private String path;
    private String controller;
    private String method;
    private Object payload;
    private String message;

    public StructuredLogEvent(String stage, String httpMethod, String path, String controller, String method, Object payload) {
        this(stage, httpMethod, path, controller, method, payload, null);
    }

    public static StructuredLogEvent buildGeneric(Logger logger, String message, Object payload) {
        return new StructuredLogEvent(
                "MSG",
                "N/A",
                logger.getName(),
                Thread.currentThread().getStackTrace()[3].getMethodName(),
                "GENERIC",
                payload,
                message
        );
    }

}
