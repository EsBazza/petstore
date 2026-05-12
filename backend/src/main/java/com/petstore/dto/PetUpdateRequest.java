package com.petstore.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an existing pet.
 */
public class PetUpdateRequest {

  @Size(max = 255, message = "Name cannot exceed 255 characters")
  private String name;

  @Size(max = 2000, message = "Description cannot exceed 2000 characters")
  private String description;

  @DecimalMin(value = "0.0", inclusive = true, message = "Price must be at least 0")
  private BigDecimal price;

  @Pattern(
    regexp = "^(https?://)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&/=]*)?$|^$",
    message = "Image URL must be a valid URL"
  )
  private String imageUrl;

  public PetUpdateRequest() {}

  public PetUpdateRequest(String name, String description, BigDecimal price, String imageUrl) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.imageUrl = imageUrl;
  }

  // Getters and Setters
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public String getImageUrl() { return imageUrl; }
  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
