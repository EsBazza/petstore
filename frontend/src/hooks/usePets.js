import { usePetContext } from '../context/PetContext';

/**
 * Custom hook for managing pets data.
 * Now acts as a wrapper for PetContext to maintain existing API.
 */
export const usePets = () => {
  return usePetContext();
};
