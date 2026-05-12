package com.petstore.controller;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import com.petstore.service.PetService;
import java.util.List;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Pet operations.
 */
@RestController
@RequestMapping("/api/pets")
public class PetController {

  private static final Logger log = LoggerFactory.getLogger(PetController.class);
  private final PetService petService;

  public PetController(PetService petService) {
    this.petService = petService;
  }

  /**
   * Get all pets.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<PetResponse>> getAllPets() {
    log.info("GET /api/pets - Fetching all pets");
    return ResponseEntity.ok(petService.getAllPets());
  }

  /**
   * Get a pet by ID.
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
    log.info("GET /api/pets/{} - Fetching pet", id);
    return ResponseEntity.ok(petService.getPetById(id));
  }

  /**
   * Create a new pet.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<PetResponse> createPet(@Valid @RequestBody PetCreateRequest request) {
    log.info("POST /api/pets - Creating new pet: {}", request.getName());
    PetResponse response = petService.createPet(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Update an existing pet.
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<PetResponse> updatePet(
      @PathVariable Long id, @Valid @RequestBody PetUpdateRequest request) {
    log.info("PUT /api/pets/{} - Updating pet", id);
    return ResponseEntity.ok(petService.updatePet(id, request));
  }

  /**
   * Delete a pet.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deletePet(@PathVariable Long id) {
    log.info("DELETE /api/pets/{} - Deleting pet", id);
    petService.deletePet(id);
    return ResponseEntity.noContent().build();
  }
}
