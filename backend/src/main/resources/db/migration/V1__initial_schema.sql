-- Flyway Migration: V1__initial_schema.sql
-- Initial database schema for Petstore application
-- Creates the Pet table with required columns and constraints

CREATE TABLE IF NOT EXISTS pet (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(1024),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add indexes for performance
CREATE INDEX IF NOT EXISTS idx_pet_created_at ON pet(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_pet_name ON pet(name);

-- Add table comment
COMMENT ON TABLE pet IS 'Represents a pet in the petstore catalog';
COMMENT ON COLUMN pet.id IS 'Unique identifier for the pet (auto-generated)';
COMMENT ON COLUMN pet.name IS 'Pet name (required)';
COMMENT ON COLUMN pet.description IS 'Detailed description of the pet';
COMMENT ON COLUMN pet.price IS 'Price in USD (required)';
COMMENT ON COLUMN pet.image_url IS 'URL to pet image';
COMMENT ON COLUMN pet.created_at IS 'Timestamp when pet was created';
COMMENT ON COLUMN pet.updated_at IS 'Timestamp when pet was last updated';
