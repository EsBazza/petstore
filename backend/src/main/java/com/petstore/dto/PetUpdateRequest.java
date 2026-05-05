package com.petstore.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for updating an existing pet.
 * 
 * All fields are optional to allow partial updates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequest {

  /**
   * Updated pet name (optional).
   */
  private String name;

  /**
   * Updated pet description (optional).
   */
  private String description;

  /**
   * Updated pet price (optional).
   */
  private BigDecimal price;

  /**
   * Updated pet image URL (optional).
   */
  private String imageUrl;
}
