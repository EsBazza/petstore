package com.petstore.service;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import com.petstore.entity.Pet;
import com.petstore.exception.PetNotFoundException;
import com.petstore.repository.PetRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of PetService.
 */
@Service
@Transactional
public class PetServiceImpl implements PetService {

  private static final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);
  private final PetRepository petRepository;

  public PetServiceImpl(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  @Override
  public List<PetResponse> getAllPets() {
    log.debug("Fetching all pets");
    return petRepository.findAllByOrderByCreatedAtDesc().stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public Page<PetResponse> getPetsPaginated(Pageable pageable) {
    log.debug("Fetching paginated pets: {}", pageable);
    return petRepository.findAll(pageable)
        .map(this::convertToResponse);
  }

  @Override
  public PetResponse getPetById(Long id) {
    log.debug("Fetching pet with ID: {}", id);
    Pet pet = petRepository.findById(id)
        .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    return convertToResponse(pet);
  }

  @Override
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

  @Override
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

  @Override
  public void deletePet(Long id) {
    log.debug("Deleting pet with ID: {}", id);
    
    Pet pet = petRepository.findById(id)
        .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    
    petRepository.delete(pet);
    log.info("Pet deleted successfully with ID: {}", id);
  }

  /**
   * Convert Pet entity to PetResponse DTO.
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
