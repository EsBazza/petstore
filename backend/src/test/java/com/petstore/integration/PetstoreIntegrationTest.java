package com.petstore.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.dto.PetCreateRequest;
import com.petstore.dto.PetUpdateRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the full CRUD lifecycle.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Petstore Full CRUD Integration Tests")
class PetstoreIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("Full CRUD Lifecycle: Add, View, Edit, Delete")
  void testFullCrudLifecycle() throws Exception {
    // 1. Create a pet
    PetCreateRequest createReq = new PetCreateRequest("Integration Pet", "Desc", new BigDecimal("10.00"), "");
    String postResponse = mockMvc.perform(post("/api/pets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createReq)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    
    Long petId = objectMapper.readTree(postResponse).get("id").asLong();

    // 2. View details
    mockMvc.perform(get("/api/pets/" + petId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Integration Pet"));

    // 3. Update pet
    PetUpdateRequest updateReq = new PetUpdateRequest();
    updateReq.setName("Updated Pet Name");
    mockMvc.perform(put("/api/pets/" + petId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateReq)))
        .andExpect(status().isOk());
    
    mockMvc.perform(get("/api/pets/" + petId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Pet Name"));

    // 4. Delete pet
    mockMvc.perform(delete("/api/pets/" + petId))
        .andExpect(status().isNoContent());

    // 5. Verify deletion
    mockMvc.perform(get("/api/pets/" + petId))
        .andExpect(status().isNotFound());
  }
}
