package com.petstore.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Pet entity representing a pet in the petstore catalog.
 * 
 * This entity maps to the 'pet' table in the database and contains
 * information about individual pets available in the store.
 */
@Entity
@Table(name = "pet")
@EntityListeners(AuditingEntityListener.class)
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
  @DecimalMin(value = "0.0", inclusive = true, message = "Price must be at least 0")
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
  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * Timestamp when the pet was last updated (auto-set).
   */
  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public Pet() {}

  public Pet(Long id, String name, String description, BigDecimal price, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imageUrl = imageUrl;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public String getImageUrl() { return imageUrl; }
  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
