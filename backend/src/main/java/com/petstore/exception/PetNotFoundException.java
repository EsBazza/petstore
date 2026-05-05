package com.petstore.exception;

/**
 * Exception thrown when a requested pet is not found.
 */
public class PetNotFoundException extends RuntimeException {

  /**
   * Constructs a PetNotFoundException with the specified message.
   *
   * @param message the error message
   */
  public PetNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a PetNotFoundException with message and cause.
   *
   * @param message the error message
   * @param cause the cause of the exception
   */
  public PetNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
