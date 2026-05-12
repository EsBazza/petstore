package com.petstore.config;

import com.petstore.exception.PetNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for the Petstore API.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handle PetNotFoundException.
   */
  @ExceptionHandler(PetNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePetNotFoundException(
      PetNotFoundException ex, WebRequest request) {
    log.error("Pet not found: {}", ex.getMessage());
    ErrorResponse error = ErrorResponse.builder()
        .timestamp(System.currentTimeMillis())
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .path(request.getDescription(false))
        .build();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle validation exceptions.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> details = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      details.put(fieldName, errorMessage);
    });

    log.error("Validation failed: {}", details);
    ErrorResponse error = ErrorResponse.builder()
        .timestamp(System.currentTimeMillis())
        .status(HttpStatus.BAD_REQUEST.value())
        .message("Validation failed")
        .path(request.getDescription(false))
        .details(details)
        .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle generic exceptions.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(
      Exception ex, WebRequest request) {
    log.error("An unexpected error occurred: ", ex);
    ErrorResponse error = ErrorResponse.builder()
        .timestamp(System.currentTimeMillis())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("An unexpected error occurred")
        .path(request.getDescription(false))
        .build();
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
