import { describe, it, expect, beforeEach, vi } from 'vitest'
import { renderHook, act, waitFor } from '@testing-library/react'
import { usePets } from '../usePets'
import * as api from '../../services/api'

/**
 * Unit tests for usePets hook
 */

vi.mock('../../services/api')

describe('usePets', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('should fetch all pets on mount', async () => {
    const mockPets = [
      { id: 1, name: 'Fluffy', price: 99.99 },
      { id: 2, name: 'Max', price: 149.99 },
    ]
    api.getPets.mockResolvedValue(mockPets)

    const { result } = renderHook(() => usePets())

    expect(result.current.loading).toBe(true)

    await waitFor(() => {
      expect(result.current.loading).toBe(false)
    })

    expect(result.current.pets).toEqual(mockPets)
    expect(api.getPets).toHaveBeenCalledTimes(1)
  })

  it('should handle error when fetching pets fails', async () => {
    const mockError = new Error('Failed to fetch')
    api.getPets.mockRejectedValue(mockError)

    const { result } = renderHook(() => usePets())

    await waitFor(() => {
      expect(result.current.loading).toBe(false)
    })

    expect(result.current.error).toBeTruthy()
    expect(result.current.pets).toEqual([])
  })

  it('should add new pet', async () => {
    const existingPets = [{ id: 1, name: 'Fluffy', price: 99.99 }]
    const newPet = { id: 2, name: 'Max', price: 149.99 }

    api.getPets.mockResolvedValue(existingPets)
    api.createPet.mockResolvedValue(newPet)

    const { result } = renderHook(() => usePets())

    await waitFor(() => {
      expect(result.current.loading).toBe(false)
    })

    await act(async () => {
      await result.current.addPet(newPet)
    })

    expect(result.current.pets).toContainEqual(newPet)
    expect(api.createPet).toHaveBeenCalledWith(newPet)
  })

  it('should remove pet', async () => {
    const mockPets = [
      { id: 1, name: 'Fluffy', price: 99.99 },
      { id: 2, name: 'Max', price: 149.99 },
    ]
    api.getPets.mockResolvedValue(mockPets)
    api.deletePet.mockResolvedValue(undefined)

    const { result } = renderHook(() => usePets())

    await waitFor(() => {
      expect(result.current.loading).toBe(false)
    })

    await act(async () => {
      await result.current.removePet(1)
    })

    expect(result.current.pets).toEqual([{ id: 2, name: 'Max', price: 149.99 }])
    expect(api.deletePet).toHaveBeenCalledWith(1)
  })
})
