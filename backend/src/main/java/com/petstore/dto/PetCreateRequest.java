package com.petstore.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating a new pet.
 * 
 * Contains validated fields required to create a pet in the petstore.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetCreateRequest {

  /**
   * Pet name (required, max 255 characters).
   */
  @NotBlank(message = "Pet name is required")
  private String name;

  /**
   * Pet description (optional).
   */
  private String description;

  /**
   * Pet price in USD (required, must be greater than 0).
   */
  @NotNull(message = "Pet price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  private BigDecimal price;

  /**
   * URL to pet image (optional, must be valid URL if provided).
   */
  @Pattern(
    regexp = "^(https?://)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&/=]*)?$|^$",
    message = "Image URL must be a valid URL"
  )
  private String imageUrl;
}
