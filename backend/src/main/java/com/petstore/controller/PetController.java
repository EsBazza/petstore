package com.petstore.controller;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import com.petstore.service.PetService;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 
 * Handles HTTP requests for CRUD operations on pets.
 */
@Slf4j
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

  private final PetService petService;

  /**
   * Get all pets.
   *
   * @return List of all pets
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<PetResponse>> getAllPets() {
    log.info("GET /api/pets - Fetching all pets");
    return ResponseEntity.ok(petService.getAllPets());
  }

  /**
   * Get a pet by ID.
   *
   * @param id the pet ID
   * @return the pet details
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
    log.info("GET /api/pets/{} - Fetching pet", id);
    return ResponseEntity.ok(petService.getPetById(id));
  }

  /**
   * Create a new pet.
   *
   * @param request the pet creation request
   * @return the created pet
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<PetResponse> createPet(@Valid @RequestBody PetCreateRequest request) {
    log.info("POST /api/pets - Creating new pet");
    return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(request));
  }

  /**
   * Update an existing pet.
   *
   * @param id the pet ID
   * @param request the pet update request
   * @return the updated pet
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
   *
   * @param id the pet ID
   * @return no content response
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deletePet(@PathVariable Long id) {
    log.info("DELETE /api/pets/{} - Deleting pet", id);
    petService.deletePet(id);
    return ResponseEntity.noContent().build();
  }
}
