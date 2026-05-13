import axios from 'axios'

/**
 * API client for Petstore backend.
 * 
 * Configures Axios with base URL and handles common error scenarios.
 */

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api'

const apiClient = axios.create({
  baseURL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * Error interceptor for consistent error handling
 */
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      // Server responded with error status
      console.error(`API Error: ${error.response.status}`, error.response.data)
    } else if (error.request) {
      // Request made but no response received
      console.error('No response from server:', error.request)
    } else {
      // Error in request setup
      console.error('Error setting up request:', error.message)
    }
    return Promise.reject(error)
  },
)

/**
 * Get all pets
 * 
 * @returns {Promise<Array>} Array of pets
 */
export const getPets = async () => {
  const response = await apiClient.get('/pets')
  return response.data
}

/**
 * Get a single pet by ID
 * 
 * @param {number} id - Pet ID
 * @returns {Promise<Object>} Pet object
 */
export const getPetById = async (id) => {
  const response = await apiClient.get(`/pets/${id}`)
  return response.data
}

/**
 * Create a new pet
 * 
 * @param {Object} petData - Pet data to create
 * @returns {Promise<Object>} Created pet object
 */
export const createPet = async (petData) => {
  const response = await apiClient.post('/pets', petData)
  return response.data
}

/**
 * Update an existing pet
 * 
 * @param {number} id - Pet ID
 * @param {Object} petData - Updated pet data
 * @returns {Promise<Object>} Updated pet object
 */
export const updatePet = async (id, petData) => {
  const response = await apiClient.put(`/pets/${id}`, petData)
  return response.data
}

/**
 * Delete a pet
 * 
 * @param {number} id - Pet ID
 * @returns {Promise<void>}
 */
export const deletePet = async (id) => {
  await apiClient.delete(`/pets/${id}`)
}

export default apiClient
