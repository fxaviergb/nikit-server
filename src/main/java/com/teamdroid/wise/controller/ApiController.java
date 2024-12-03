package com.teamdroid.wise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api")
public class ApiController {

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok("Data from secured endpoint");
    }

    @PostMapping("/data")
    public ResponseEntity<String> postData(@RequestBody Map<String, String> data) {
        return ResponseEntity.ok("Received: " + data.get("info"));
    }
}
