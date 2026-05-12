package com.petstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import com.petstore.entity.Pet;
import com.petstore.exception.PetNotFoundException;
import com.petstore.repository.PetRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for PetService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PetService Tests")
class PetServiceTest {

  @Mock
  private PetRepository petRepository;

  @InjectMocks
  private PetServiceImpl petService;

  private Pet testPet;

  @BeforeEach
  void setUp() {
    testPet = new Pet();
    testPet.setId(1L);
    testPet.setName("Fluffy");
    testPet.setDescription("A cute dog");
    testPet.setPrice(new BigDecimal("99.99"));
    testPet.setImageUrl("https://example.com/dog.jpg");
    testPet.setCreatedAt(LocalDateTime.now());
    testPet.setUpdatedAt(LocalDateTime.now());
  }

  @Test
  @DisplayName("Should retrieve all pets")
  void testGetAllPets() {
    // Arrange
    List<Pet> pets = new ArrayList<>();
    pets.add(testPet);
    when(petRepository.findAllByOrderByCreatedAtDesc()).thenReturn(pets);

    // Act
    List<PetResponse> result = petService.getAllPets();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Fluffy", result.get(0).getName());
    verify(petRepository, times(1)).findAllByOrderByCreatedAtDesc();
  }

  @Test
  @DisplayName("Should retrieve pet by ID")
  void testGetPetById() {
    // Arrange
    when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));

    // Act
    PetResponse result = petService.getPetById(1L);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Fluffy", result.getName());
    verify(petRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Should throw PetNotFoundException when pet not found by ID")
  void testGetPetByIdNotFound() {
    // Arrange
    when(petRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PetNotFoundException.class, () -> petService.getPetById(999L));
    verify(petRepository, times(1)).findById(999L);
  }

  @Test
  @DisplayName("Should create new pet")
  void testCreatePet() {
    // Arrange
    PetCreateRequest request = new PetCreateRequest();
    request.setName("Fluffy");
    request.setDescription("A cute dog");
    request.setPrice(new BigDecimal("99.99"));
    request.setImageUrl("https://example.com/dog.jpg");

    when(petRepository.save(any(Pet.class))).thenReturn(testPet);

    // Act
    PetResponse result = petService.createPet(request);

    // Assert
    assertNotNull(result);
    assertEquals("Fluffy", result.getName());
    verify(petRepository, times(1)).save(any(Pet.class));
  }

  @Test
  @DisplayName("Should update existing pet")
  void testUpdatePet() {
    // Arrange
    PetUpdateRequest request = new PetUpdateRequest();
    request.setName("Updated Name");
    request.setPrice(new BigDecimal("150.00"));

    when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));
    when(petRepository.save(any(Pet.class))).thenReturn(testPet);

    // Act
    PetResponse result = petService.updatePet(1L, request);

    // Assert
    assertNotNull(result);
    assertEquals("Updated Name", result.getName());
    assertEquals(new BigDecimal("150.00"), result.getPrice());
    verify(petRepository, times(1)).findById(1L);
    verify(petRepository, times(1)).save(testPet);
  }

  @Test
  @DisplayName("Should partial update existing pet")
  void testUpdatePetPartial() {
    // Arrange
    PetUpdateRequest request = new PetUpdateRequest();
    request.setName("Partial Update");

    when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));
    when(petRepository.save(any(Pet.class))).thenReturn(testPet);

    // Act
    PetResponse result = petService.updatePet(1L, request);

    // Assert
    assertNotNull(result);
    assertEquals("Partial Update", result.getName());
    assertEquals(new BigDecimal("99.99"), result.getPrice()); // Price should remain unchanged
    verify(petRepository, times(1)).findById(1L);
    verify(petRepository, times(1)).save(testPet);
  }

  @Test
  @DisplayName("Should delete pet")
  void testDeletePet() {
    // Arrange
    when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));

    // Act
    petService.deletePet(1L);

    // Assert
    verify(petRepository, times(1)).findById(1L);
    verify(petRepository, times(1)).delete(testPet);
  }

  @Test
  @DisplayName("Should throw PetNotFoundException when deleting non-existent pet")
  void testDeletePetNotFound() {
    // Arrange
    when(petRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PetNotFoundException.class, () -> petService.deletePet(999L));
    verify(petRepository, times(1)).findById(999L);
  }
}
