/**
 * Constants for the Petstore application
 */

// API endpoints
export const API_ENDPOINTS = {
  PETS: '/pets',
  PET_DETAIL: (id) => `/pets/${id}`,
}

// Validation messages
export const VALIDATION_MESSAGES = {
  NAME_REQUIRED: 'Pet name is required',
  PRICE_REQUIRED: 'Price is required',
  PRICE_INVALID: 'Price must be greater than 0',
  IMAGE_URL_INVALID: 'Image URL must be a valid URL',
}

// UI constants
export const UI_CONSTANTS = {
  GRID_COLUMNS_MOBILE: 1,
  GRID_COLUMNS_TABLET: 2,
  GRID_COLUMNS_DESKTOP: 3,
  GRID_SPACING: 3,
  DEFAULT_IMAGE: 'https://via.placeholder.com/300x200?text=No+Image',
}

// Currency formatting
export const CURRENCY_FORMATTER = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: 'USD',
})
