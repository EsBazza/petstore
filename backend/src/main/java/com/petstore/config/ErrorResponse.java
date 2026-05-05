package com.petstore.config;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard error response format for API errors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  /**
   * HTTP status code.
   */
  private int status;

  /**
   * Error message.
   */
  private String message;

  /**
   * Error timestamp.
   */
  private long timestamp;

  /**
   * Path that caused the error.
   */
  private String path;

  /**
   * Additional error details (e.g., validation errors).
   */
  private Map<String, String> details;
}
