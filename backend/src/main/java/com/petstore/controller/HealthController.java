package com.petstore.controller;

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
@RequestMapping("/api/health")
public class HealthController {

  /**
   * Health check endpoint.
   *
   * @return success message
   */
  @GetMapping
  public String health() {
    return "OK";
  }
}
