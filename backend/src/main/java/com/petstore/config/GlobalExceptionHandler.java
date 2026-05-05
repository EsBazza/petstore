package com.petstore.config;

import com.petstore.exception.PetNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for the application.
 * 
 * Handles exceptions across all controllers and provides consistent error responses.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle PetNotFoundException.
   *
   * @param ex the exception
   * @param request the web request
   * @return 404 error response
   */
  @ExceptionHandler(PetNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handlePetNotFoundException(
      PetNotFoundException ex, WebRequest request) {
    log.error("Pet not found: {}", ex.getMessage());
    
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .timestamp(System.currentTimeMillis())
        .path(request.getDescription(false).replace("uri=", ""))
        .build();
    
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handle validation errors by overriding ResponseEntityExceptionHandler method.
   *
   * @param ex the exception
   * @param headers the headers
   * @param status the status
   * @param request the web request
   * @return 400 error response with validation details
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    
    Map<String, String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            error -> error.getField(),
            error -> error.getDefaultMessage(),
            (existing, replacement) -> existing
        ));
    
    log.warn("Validation error: {}", errors);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(status.value())
        .message("Validation failed")
        .timestamp(System.currentTimeMillis())
        .path(request.getDescription(false).replace("uri=", ""))
        .details(errors)
        .build();
    
    return ResponseEntity.status(status).body(errorResponse);
  }

  /**
   * Handle general exceptions.
   *
   * @param ex the exception
   * @param request the web request
   * @return 500 error response
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleGeneralException(
      Exception ex, WebRequest request) {
    
    log.error("Unexpected error occurred", ex);
    
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("An unexpected error occurred. Please try again later.")
        .timestamp(System.currentTimeMillis())
        .path(request.getDescription(false).replace("uri=", ""))
        .build();
    
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
