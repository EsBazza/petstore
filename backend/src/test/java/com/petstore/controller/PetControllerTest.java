package com.petstore.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetResponse;
import com.petstore.dto.PetUpdateRequest;
import com.petstore.exception.PetNotFoundException;
import com.petstore.service.PetService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for PetController.
 */
@WebMvcTest(PetController.class)
@DisplayName("PetController Tests")
class PetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PetService petService;

  @Autowired
  private ObjectMapper objectMapper;

  private PetResponse testPetResponse;

  @BeforeEach
  void setUp() {
    testPetResponse = new PetResponse();
    testPetResponse.setId(1L);
    testPetResponse.setName("Fluffy");
    testPetResponse.setDescription("A cute dog");
    testPetResponse.setPrice(new BigDecimal("99.99"));
    testPetResponse.setImageUrl("https://example.com/dog.jpg");
    testPetResponse.setCreatedAt(LocalDateTime.now());
    testPetResponse.setUpdatedAt(LocalDateTime.now());
  }

  @Test
  @DisplayName("Should return 200 OK with list of pets - T057")
  void testGetAllPetsReturns200() throws Exception {
    // Arrange
    List<PetResponse> pets = new ArrayList<>();
    pets.add(testPetResponse);
    when(petService.getAllPets()).thenReturn(pets);

    // Act & Assert
    mockMvc.perform(get("/api/pets"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Fluffy"))
        .andExpect(jsonPath("$[0].price").value(99.99));
  }

  @Test
  @DisplayName("Should return 200 OK with single pet - T113")
  void testGetPetByIdReturns200() throws Exception {
    // Arrange
    when(petService.getPetById(1L)).thenReturn(testPetResponse);

    // Act & Assert
    mockMvc.perform(get("/api/pets/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Fluffy"));
  }

  @Test
  @DisplayName("Should return 404 when pet not found - T114")
  void testGetPetByIdReturns404() throws Exception {
    // Arrange
    when(petService.getPetById(999L)).thenThrow(new PetNotFoundException("Pet not found"));

    // Act & Assert
    mockMvc.perform(get("/api/pets/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should create pet and return 201 Created - T083")
  void testCreatePetReturns201() throws Exception {
    // Arrange
    PetCreateRequest request = new PetCreateRequest();
    request.setName("Fluffy");
    request.setPrice(new BigDecimal("99.99"));
    
    when(petService.createPet(any(PetCreateRequest.class))).thenReturn(testPetResponse);

    // Act & Assert
    mockMvc.perform(post("/api/pets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Fluffy"));
  }

  @Test
  @DisplayName("Should return 400 when creating pet with invalid data - T088")
  void testCreatePetReturns400() throws Exception {
    // Arrange
    PetCreateRequest request = new PetCreateRequest();
    request.setName(""); // Invalid name
    request.setPrice(new BigDecimal("-1.00")); // Invalid price

    // Act & Assert
    mockMvc.perform(post("/api/pets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should update pet and return 200 OK - T109")
  void testUpdatePetReturns200() throws Exception {
    // Arrange
    PetUpdateRequest request = new PetUpdateRequest();
    request.setName("Updated Name");
    
    when(petService.updatePet(any(Long.class), any(PetUpdateRequest.class))).thenReturn(testPetResponse);

    // Act & Assert
    mockMvc.perform(put("/api/pets/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Should delete pet and return 204 No Content - T132")
  void testDeletePetReturns204() throws Exception {
    // Act & Assert
    mockMvc.perform(delete("/api/pets/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Should return empty list when no pets in database - T058")
  void testGetAllPetsReturnsEmptyList() throws Exception {
    // Arrange
    List<PetResponse> pets = new ArrayList<>();
    when(petService.getAllPets()).thenReturn(pets);

    // Act & Assert
    mockMvc.perform(get("/api/pets"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @DisplayName("Should return pets in correct order (by createdAt descending) - T059")
  void testGetAllPetsReturnsInCorrectOrder() throws Exception {
    // Arrange
    List<PetResponse> pets = new ArrayList<>();
    
    PetResponse pet1 = new PetResponse();
    pet1.setId(1L);
    pet1.setName("Fluffy");
    pet1.setPrice(new BigDecimal("99.99"));
    pet1.setCreatedAt(LocalDateTime.of(2026, 5, 5, 10, 0, 0));
    
    PetResponse pet2 = new PetResponse();
    pet2.setId(2L);
    pet2.setName("Buddy");
    pet2.setPrice(new BigDecimal("79.99"));
    pet2.setCreatedAt(LocalDateTime.of(2026, 5, 5, 11, 0, 0));
    
    pets.add(pet2); // Newer pet first
    pets.add(pet1); // Older pet second
    
    when(petService.getAllPets()).thenReturn(pets);

    // Act & Assert
    mockMvc.perform(get("/api/pets"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(2L))
        .andExpect(jsonPath("$[0].name").value("Buddy"))
        .andExpect(jsonPath("$[1].id").value(1L))
        .andExpect(jsonPath("$[1].name").value("Fluffy"));
  }

  @Test
  @DisplayName("Should have appropriate HTTP headers in response - T060")
  void testGetAllPetsResponseHeaders() throws Exception {
    // Arrange
    List<PetResponse> pets = new ArrayList<>();
    pets.add(testPetResponse);
    when(petService.getAllPets()).thenReturn(pets);

    // Act & Assert
    mockMvc.perform(get("/api/pets"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(result -> {
          String contentType = result.getResponse().getContentType();
          assert contentType != null && contentType.contains("application/json");
        });
  }
}
