package com.petstore.repository;

import com.petstore.entity.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Pet entity.
 * 
 * Provides CRUD operations and custom query methods for Pet entities.
 * Spring Data JPA automatically implements these methods at runtime.
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

  /**
   * Find all pets ordered by creation date (newest first).
   *
   * @return List of pets sorted by createdAt descending
   */
  List<Pet> findAllByOrderByCreatedAtDesc();

  /**
   * Find pets by name (case-insensitive).
   *
   * @param name the pet name to search for
   * @return List of pets matching the name
   */
  List<Pet> findByNameIgnoreCase(String name);

  /**
   * Find pets by partial name match (case-insensitive).
   *
   * @param nameFragment partial name to search for
   * @return List of pets matching the fragment
   */
  List<Pet> findByNameIgnoreCaseContaining(String nameFragment);
}
