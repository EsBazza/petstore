import { useState, useEffect } from 'react'
import { toast } from 'sonner'
import * as api from '../services/api'

/**
 * Custom hook for managing pets data.
 * 
 * Handles fetching, creating, updating, and deleting pets with optimistic updates.
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
      const msg = err.response?.data?.message || 'Failed to fetch pets'
      setError(msg)
      toast.error(msg)
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
   * Add new pet (Optimistic)
   */
  const addPet = async (petData) => {
    const previousPets = [...pets]
    // Create a temporary pet for optimistic UI
    const tempId = Date.now()
    const optimisticPet = { 
      ...petData, 
      id: tempId, 
      isOptimistic: true,
      createdAt: new Date().toISOString() 
    }
    
    setPets([optimisticPet, ...pets])
    
    try {
      setError(null)
      const newPet = await api.createPet(petData)
      // Replace optimistic pet with the real one from server
      setPets((current) => current.map(p => p.id === tempId ? newPet : p))
      toast.success('Pet added successfully!')
      return newPet
    } catch (err) {
      // Rollback
      setPets(previousPets)
      const msg = err.response?.data?.message || 'Failed to create pet'
      setError(msg)
      toast.error(msg)
      console.error('Error creating pet:', err)
      return null
    }
  }

  /**
   * Update existing pet (Optimistic)
   */
  const updatePetData = async (id, petData) => {
    const previousPets = [...pets]
    
    setPets((current) => current.map((pet) => 
      pet.id === id ? { ...pet, ...petData, isOptimistic: true } : pet
    ))

    try {
      setError(null)
      const updatedPet = await api.updatePet(id, petData)
      setPets((current) => current.map((pet) => (pet.id === id ? updatedPet : pet)))
      toast.success('Pet updated successfully!')
      return updatedPet
    } catch (err) {
      // Rollback
      setPets(previousPets)
      const msg = err.response?.data?.message || 'Failed to update pet'
      setError(msg)
      toast.error(msg)
      console.error('Error updating pet:', err)
      return null
    }
  }

  /**
   * Remove pet (Optimistic)
   */
  const removePet = async (id) => {
    const previousPets = [...pets]
    
    // Optimistically remove from list
    setPets((current) => current.filter((pet) => pet.id !== id))
    
    try {
      setError(null)
      await api.deletePet(id)
      toast.success('Pet deleted successfully!')
      return true
    } catch (err) {
      // Rollback
      setPets(previousPets)
      const msg = err.response?.data?.message || 'Failed to delete pet'
      setError(msg)
      toast.error(msg)
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
