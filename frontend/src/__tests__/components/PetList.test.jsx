import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import PetList from '../../components/PetList';
import * as api from '../../services/api';

// Mock the api service
vi.mock('../../services/api');

// Mock the PetCard component
vi.mock('../../components/PetCard', () => ({
  default: ({ pet }) => <div data-testid={`pet-card-${pet.id}`}>{pet.name}</div>,
}));

describe('PetList Component - T069-T073', () => {
  const mockPets = [
    {
      id: 1,
      name: 'Fluffy',
      description: 'A cute dog',
      price: 99.99,
      imageUrl: 'https://example.com/dog.jpg',
    },
    {
      id: 2,
      name: 'Buddy',
      description: 'A friendly cat',
      price: 79.99,
      imageUrl: 'https://example.com/cat.jpg',
    },
  ];

  beforeEach(() => {
    vi.clearAllMocks();
  });

  /**
   * T069: Should render list of pets from API
   */
  it('T069: Should render list of pets from API', async () => {
    api.getPets.mockResolvedValueOnce(mockPets);

    render(<PetList />);

    await waitFor(() => {
      expect(screen.getByText('Fluffy')).toBeInTheDocument();
      expect(screen.getByText('Buddy')).toBeInTheDocument();
    });
  });

  /**
   * T070: Should display loading state while fetching pets
   */
  it('T070: Should display loading state while fetching pets', async () => {
    api.getPets.mockImplementation(
      () =>
        new Promise((resolve) => {
          setTimeout(() => resolve(mockPets), 500);
        })
    );

    const { container } = render(<PetList />);

    // Check for CircularProgress (loading indicator)
    const spinner = container.querySelector('[class*="MuiCircularProgress"]');
    expect(spinner).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText('Fluffy')).toBeInTheDocument();
    });
  });

  /**
   * T071: Should display error message if API fails
   */
  it('T071: Should display error message if API fails', async () => {
    const errorMessage = 'Failed to fetch pets';
    api.getPets.mockRejectedValueOnce(new Error(errorMessage));

    render(<PetList />);

    await waitFor(() => {
      expect(screen.getByText(/Failed to fetch pets/i)).toBeInTheDocument();
      expect(screen.getByRole('button', { name: /Retry/i })).toBeInTheDocument();
    });
  });

  /**
   * T072: Should display empty state when no pets available
   */
  it('T072: Should display empty state message when no pets available', async () => {
    api.getPets.mockResolvedValueOnce([]);

    render(<PetList />);

    await waitFor(() => {
      expect(screen.getByText('No pets available')).toBeInTheDocument();
      expect(screen.getByText('Add one to get started!')).toBeInTheDocument();
    });
  });

  /**
   * T073: Should render responsive grid layout with correct columns
   */
  it('T073: Should render responsive grid layout', async () => {
    api.getPets.mockResolvedValueOnce(mockPets);

    const { container } = render(<PetList />);

    await waitFor(() => {
      // Check for MUI Grid container
      const gridContainer = container.querySelector('[class*="MuiGrid-container"]');
      expect(gridContainer).toBeInTheDocument();

      // Check for grid items
      const gridItems = container.querySelectorAll('[class*="MuiGrid-item"]');
      expect(gridItems.length).toBeGreaterThanOrEqual(2);
    });
  });
});
