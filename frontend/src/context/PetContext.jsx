import React, { createContext, useContext, useState, useEffect, useMemo } from 'react';
import { toast } from 'sonner';
import * as api from '../services/api';

const PetContext = createContext();

/**
 * Provider component for Pets context.
 * Manages global pet state and operations.
 */
export const PetProvider = ({ children }) => {
  const [pets, setPets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  /**
   * Fetch all pets from API
   */
  const fetchPets = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await api.getPets();
      // Handle both paginated (data.content) and non-paginated (data) responses
      setPets(Array.isArray(data) ? data : (data.content || []));
    } catch (err) {
      const msg = err.response?.data?.message || 'Failed to fetch pets';
      setError(msg);
      toast.error(msg);
      console.error('Error fetching pets:', err);
    } finally {
      setLoading(false);
    }
  };

  /**
   * Add new pet (Optimistic)
   */
  const addPet = async (petData) => {
    const previousPets = [...pets];
    const tempId = `temp-${Date.now()}`;
    const optimisticPet = { 
      ...petData, 
      id: tempId, 
      isOptimistic: true,
      createdAt: new Date().toISOString() 
    };
    
    setPets([optimisticPet, ...pets]);
    
    try {
      setError(null);
      const newPet = await api.createPet(petData);
      setPets((current) => current.map(p => p.id === tempId ? newPet : p));
      toast.success('Pet added successfully!');
      return newPet;
    } catch (err) {
      setPets(previousPets);
      const msg = err.response?.data?.message || 'Failed to create pet';
      setError(msg);
      toast.error(msg);
      return null;
    }
  };

  /**
   * Update existing pet (Optimistic)
   */
  const updatePetData = async (id, petData) => {
    const previousPets = [...pets];
    
    setPets((current) => current.map((pet) => 
      pet.id === id ? { ...pet, ...petData, isOptimistic: true } : pet
    ));

    try {
      setError(null);
      const updatedPet = await api.updatePet(id, petData);
      setPets((current) => current.map((pet) => (pet.id === id ? updatedPet : pet)));
      toast.success('Pet updated successfully!');
      return updatedPet;
    } catch (err) {
      setPets(previousPets);
      const msg = err.response?.data?.message || 'Failed to update pet';
      setError(msg);
      toast.error(msg);
      return null;
    }
  };

  /**
   * Remove pet (Optimistic)
   */
  const removePet = async (id) => {
    const previousPets = [...pets];
    setPets((current) => current.filter((pet) => pet.id !== id));
    
    try {
      setError(null);
      await api.deletePet(id);
      toast.success('Pet deleted successfully!');
      return true;
    } catch (err) {
      setPets(previousPets);
      const msg = err.response?.data?.message || 'Failed to delete pet';
      setError(msg);
      toast.error(msg);
      return false;
    }
  };

  /**
   * Fetch single pet by ID
   */
  const fetchPetById = async (id) => {
    try {
      setError(null);
      return await api.getPetById(id);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch pet');
      console.error('Error fetching pet:', err);
      return null;
    }
  };

  // Fetch pets on mount
  useEffect(() => {
    fetchPets();
  }, []);

  const value = useMemo(() => ({
    pets,
    loading,
    error,
    fetchPets,
    fetchPetById,
    addPet,
    updatePetData,
    removePet
  }), [pets, loading, error]);

  return (
    <PetContext.Provider value={value}>
      {children}
    </PetContext.Provider>
  );
};

/**
 * Hook to use the PetContext
 */
export const usePetContext = () => {
  const context = useContext(PetContext);
  if (!context) {
    throw new Error('usePetContext must be used within a PetProvider');
  }
  return context;
};
