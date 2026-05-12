package com.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main entry point for Petstore Spring Boot application.
 * 
 * This application provides a REST API for managing pets in a petstore.
 * Features include browsing, creating, updating, and deleting pet listings.
 */
@SpringBootApplication
@EnableJpaAuditing
public class PetstoreApplication {

  /**
   * Application entry point.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(PetstoreApplication.class, args);
  }
}
