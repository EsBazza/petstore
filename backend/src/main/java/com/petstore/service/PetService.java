package com.petstore.service;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import java.util.List;

/**
 * Interface for Pet management services.
 */
public interface PetService {
  List<PetResponse> getAllPets();
  PetResponse getPetById(Long id);
  PetResponse createPet(PetCreateRequest request);
  PetResponse updatePet(Long id, PetUpdateRequest request);
  void deletePet(Long id);
}
