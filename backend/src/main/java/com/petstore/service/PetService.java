package com.petstore.service;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for Pet management services.
 */
public interface PetService {
  List<PetResponse> getAllPets();
  Page<PetResponse> getPetsPaginated(Pageable pageable);
  PetResponse getPetById(Long id);
  PetResponse createPet(PetCreateRequest request);
  PetResponse updatePet(Long id, PetUpdateRequest request);
  void deletePet(Long id);
}
