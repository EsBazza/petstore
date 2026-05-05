package com.petstore.service;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import com.petstore.entity.Pet;
import com.petstore.exception.PetNotFoundException;
import com.petstore.repository.PetRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for Pet management.
 * 
 * Contains business logic for pet operations, validation, and data transformation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PetService {

  private final PetRepository petRepository;

  /**
   * Get all pets ordered by creation date (newest first).
   *
   * @return List of all pets as DTOs
   */
  public List<PetResponse> getAllPets() {
    log.debug("Fetching all pets");
    return petRepository.findAllByOrderByCreatedAtDesc().stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  /**
   * Get a pet by ID.
   *
   * @param id the pet ID
   * @return the pet as DTO
   * @throws PetNotFoundException if pet not found
   */
  public PetResponse getPetById(Long id) {
    log.debug("Fetching pet with ID: {}", id);
    Pet pet = petRepository.findById(id)
        .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    return convertToResponse(pet);
  }

  /**
   * Create a new pet.
   *
   * @param request the pet creation request
   * @return the created pet as DTO
   */
  public PetResponse createPet(PetCreateRequest request) {
    log.debug("Creating new pet with name: {}", request.getName());
    
    Pet pet = new Pet();
    pet.setName(request.getName());
    pet.setDescription(request.getDescription());
    pet.setPrice(request.getPrice());
    pet.setImageUrl(request.getImageUrl());
    
    Pet savedPet = petRepository.save(pet);
    log.info("Pet created successfully with ID: {}", savedPet.getId());
    
    return convertToResponse(savedPet);
  }

  /**
   * Update an existing pet.
   *
   * @param id the pet ID
   * @param request the pet update request
   * @return the updated pet as DTO
   * @throws PetNotFoundException if pet not found
   */
  public PetResponse updatePet(Long id, PetUpdateRequest request) {
    log.debug("Updating pet with ID: {}", id);
    
    Pet pet = petRepository.findById(id)
        .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    
    if (request.getName() != null) {
      pet.setName(request.getName());
    }
    if (request.getDescription() != null) {
      pet.setDescription(request.getDescription());
    }
    if (request.getPrice() != null) {
      pet.setPrice(request.getPrice());
    }
    if (request.getImageUrl() != null) {
      pet.setImageUrl(request.getImageUrl());
    }
    
    Pet updatedPet = petRepository.save(pet);
    log.info("Pet updated successfully with ID: {}", updatedPet.getId());
    
    return convertToResponse(updatedPet);
  }

  /**
   * Delete a pet by ID.
   *
   * @param id the pet ID
   * @throws PetNotFoundException if pet not found
   */
  public void deletePet(Long id) {
    log.debug("Deleting pet with ID: {}", id);
    
    Pet pet = petRepository.findById(id)
        .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    
    petRepository.delete(pet);
    log.info("Pet deleted successfully with ID: {}", id);
  }

  /**
   * Convert Pet entity to PetResponse DTO.
   *
   * @param pet the pet entity
   * @return the pet response DTO
   */
  private PetResponse convertToResponse(Pet pet) {
    return new PetResponse(
        pet.getId(),
        pet.getName(),
        pet.getDescription(),
        pet.getPrice(),
        pet.getImageUrl(),
        pet.getCreatedAt(),
        pet.getUpdatedAt()
    );
  }
}
