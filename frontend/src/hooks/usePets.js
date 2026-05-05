import { useState, useEffect } from 'react'
import * as api from '../services/api'

/**
 * Custom hook for managing pets data.
 * 
 * Handles fetching, creating, updating, and deleting pets.
 */
export const usePets = () => {
  const [pets, setPets] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  /**
   * Fetch all pets from API
   */
  const fetchPets = async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await api.getPets()
      setPets(data)
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch pets')
      console.error('Error fetching pets:', err)
    } finally {
      setLoading(false)
    }
  }

  /**
   * Fetch single pet by ID
   */
  const fetchPetById = async (id) => {
    try {
      setError(null)
      return await api.getPetById(id)
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch pet')
      console.error('Error fetching pet:', err)
      return null
    }
  }

  /**
   * Add new pet
   */
  const addPet = async (petData) => {
    try {
      setError(null)
      const newPet = await api.createPet(petData)
      setPets([newPet, ...pets])
      return newPet
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create pet')
      console.error('Error creating pet:', err)
      return null
    }
  }

  /**
   * Update existing pet
   */
  const updatePetData = async (id, petData) => {
    try {
      setError(null)
      const updatedPet = await api.updatePet(id, petData)
      setPets(pets.map((pet) => (pet.id === id ? updatedPet : pet)))
      return updatedPet
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update pet')
      console.error('Error updating pet:', err)
      return null
    }
  }

  /**
   * Remove pet
   */
  const removePet = async (id) => {
    try {
      setError(null)
      await api.deletePet(id)
      setPets(pets.filter((pet) => pet.id !== id))
      return true
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete pet')
      console.error('Error deleting pet:', err)
      return false
    }
  }

  // Fetch pets on mount
  useEffect(() => {
    fetchPets()
  }, [])

  return {
    pets,
    loading,
    error,
    fetchPets,
    fetchPetById,
    addPet,
    updatePetData,
    removePet,
  }
}
