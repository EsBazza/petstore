package com.petstore.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pet entity representing a pet in the petstore catalog.
 * 
 * This entity maps to the 'pet' table in the database and contains
 * information about individual pets available in the store.
 */
@Entity
@Table(name = "pet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

  /**
   * Unique identifier for the pet (auto-generated).
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Name of the pet (required, max 255 characters).
   */
  @NotBlank(message = "Pet name is required")
  @Column(nullable = false, length = 255)
  private String name;

  /**
   * Description of the pet (optional, unlimited text).
   */
  @Column(columnDefinition = "TEXT")
  private String description;

  /**
   * Price of the pet in USD (required, minimum 0).
   */
  @NotNull(message = "Pet price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  /**
   * URL to the pet's image (optional, max 1024 characters).
   */
  @Pattern(
    regexp = "^(https?://)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&/=]*)?$|^$",
    message = "Image URL must be a valid URL"
  )
  @Column(length = 1024)
  private String imageUrl;

  /**
   * Timestamp when the pet was created (auto-set).
   */
  @NotNull
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * Timestamp when the pet was last updated (auto-set).
   */
  @NotNull
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  /**
   * Callback to set createdAt before persisting.
   */
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  /**
   * Callback to update updatedAt before updating.
   */
  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
