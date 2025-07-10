package com.teamdroid.nikit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling API endpoints.
 *
 * <p>This controller provides endpoints for sending and retrieving data from a secure API. It
 * includes Swagger/OpenAPI annotations for detailed documentation of the API behavior.
 */
@RestController
@RequestMapping("/v1/api")
public class ApiController {

    /**
     * Retrieves data from the secure endpoint.
     *
     * @return A {@code ResponseEntity} containing a success message if authorized.
     */
    @Operation(summary = "Retrieves data from the secure endpoint")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Data retrieved successfully"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content)
            })
    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok("Data from secured endpoint");
    }

    /**
     * Sends data to the secure endpoint.
     *
     * @param data A map containing the data to be sent.
     * @return A {@code ResponseEntity} with a success message containing the received data.
     */
    @Operation(summary = "Sends data to the secure endpoint")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Data received successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content)
            })
    @PostMapping("/data")
    public ResponseEntity<String> postData(@RequestBody Map<String, String> data) {
        return ResponseEntity.ok("Received: " + data.get("info"));
    }
}
