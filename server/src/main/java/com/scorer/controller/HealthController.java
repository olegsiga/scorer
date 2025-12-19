package com.scorer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class HealthController {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public HealthController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        List<String> errors = checkHealth();
        if (errors.isEmpty()) {
            return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.ok(Map.of("status", "error", "errors", errors));
    }

    @PostMapping("/healthOK")
    public ResponseEntity<Map<String, Object>> healthOK() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    private List<String> checkHealth() {
        List<String> errors = new LinkedList<>();
        checkDbConnection(errors);
        return errors;
    }

    private void checkDbConnection(List<String> errors) {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Map.of(), Integer.class);
        } catch (Exception e) {
            errors.add("Database connection failed: " + e.getMessage());
        }
    }

}
