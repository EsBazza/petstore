package com.petstore.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for health check operations.
 * 
 * Provides an endpoint for Render or other monitoring services to verify
 * that the application is running correctly.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

  /**
   * Root endpoint - returns API information.
   *
   * @return API info object
   */
  @GetMapping
  public Map<String, Object> root() {
    return Map.of(
      "status", "running",
      "name", "Petstore API",
      "version", "1.0.0",
      "endpoints", Map.of(
        "health", "/alonzo/api/health",
        "pets", "/alonzo/api/pets",
        "petDetail", "/alonzo/api/pets/{id}"
      )
    );
  }

  /**
   * Health check endpoint.
   *
   * @return success message
   */
  @GetMapping("/health")
  public String health() {
    return "OK";
  }
}
