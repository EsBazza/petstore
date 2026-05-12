package com.petstore.config;

import java.util.Map;

/**
 * Standard error response format for API errors.
 */
public class ErrorResponse {

  private int status;
  private String message;
  private long timestamp;
  private String path;
  private Map<String, String> details;

  public ErrorResponse() {}

  public ErrorResponse(int status, String message, long timestamp, String path, Map<String, String> details) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
    this.path = path;
    this.details = details;
  }

  // Getters and Setters
  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
  public long getTimestamp() { return timestamp; }
  public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
  public String getPath() { return path; }
  public void setPath(String path) { this.path = path; }
  public Map<String, String> getDetails() { return details; }
  public void setDetails(Map<String, String> details) { this.details = details; }

  // Simple builder-like pattern for compatibility
  public static ErrorResponseBuilder builder() {
    return new ErrorResponseBuilder();
  }

  public static class ErrorResponseBuilder {
    private int status;
    private String message;
    private long timestamp;
    private String path;
    private Map<String, String> details;

    public ErrorResponseBuilder status(int status) { this.status = status; return this; }
    public ErrorResponseBuilder message(String message) { this.message = message; return this; }
    public ErrorResponseBuilder timestamp(long timestamp) { this.timestamp = timestamp; return this; }
    public ErrorResponseBuilder path(String path) { this.path = path; return this; }
    public ErrorResponseBuilder details(Map<String, String> details) { this.details = details; return this; }
    public ErrorResponse build() {
      return new ErrorResponse(status, message, timestamp, path, details);
    }
  }
}
