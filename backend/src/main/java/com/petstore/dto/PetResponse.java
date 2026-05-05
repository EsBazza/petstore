package com.petstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for pet responses in API.
 * 
 * Contains all pet information to send to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetResponse {

  /**
   * Pet unique identifier.
   */
  private Long id;

  /**
   * Pet name.
   */
  private String name;

  /**
   * Pet description.
   */
  private String description;

  /**
   * Pet price in USD.
   */
  private BigDecimal price;

  /**
   * URL to pet image.
   */
  private String imageUrl;

  /**
   * Timestamp when pet was created.
   */
  private LocalDateTime createdAt;

  /**
   * Timestamp when pet was last updated.
   */
  private LocalDateTime updatedAt;
}
