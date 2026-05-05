import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import PetCard from '../../components/PetCard';

describe('PetCard Component - T074-T077', () => {
  const mockPet = {
    id: 1,
    name: 'Fluffy',
    description: 'A cute and fluffy dog',
    price: 99.99,
    imageUrl: 'https://example.com/fluffy.jpg',
  };

  const mockCallbacks = {
    onView: vi.fn(),
    onEdit: vi.fn(),
    onDelete: vi.fn(),
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  /**
   * T074: Should render pet information (name, description, price)
   */
  it('T074: Should render pet information (name, description, price)', () => {
    render(<PetCard pet={mockPet} {...mockCallbacks} />);

    expect(screen.getByText('Fluffy')).toBeInTheDocument();
    expect(screen.getByText(/A cute and fluffy dog/)).toBeInTheDocument();
    expect(screen.getByText(/\$99.99/)).toBeInTheDocument(); // Price formatted as currency
  });

  /**
   * T075: Should render pet image with correct src attribute
   */
  it('T075: Should render pet image with correct src attribute', () => {
    render(<PetCard pet={mockPet} {...mockCallbacks} />);

    const image = screen.getByAltText('Fluffy');
    expect(image).toHaveAttribute('src', 'https://example.com/fluffy.jpg');
  });

  /**
   * T076: Should render action buttons that are clickable
   */
  it('T076: Should render action buttons that are clickable', () => {
    render(<PetCard pet={mockPet} {...mockCallbacks} />);

    const viewButton = screen.getByRole('button', { name: /View/i });
    const editButton = screen.getByRole('button', { name: /Edit/i });
    const deleteButton = screen.getByRole('button', { name: /Delete/i });

    expect(viewButton).toBeInTheDocument();
    expect(editButton).toBeInTheDocument();
    expect(deleteButton).toBeInTheDocument();

    // Test button clicks
    fireEvent.click(viewButton);
    expect(mockCallbacks.onView).toHaveBeenCalledOnce();

    fireEvent.click(editButton);
    expect(mockCallbacks.onEdit).toHaveBeenCalledOnce();

    fireEvent.click(deleteButton);
    expect(mockCallbacks.onDelete).toHaveBeenCalledOnce();
  });

  /**
   * T077: Should display fallback placeholder if image URL fails
   */
  it('T077: Should display fallback placeholder if image URL fails', () => {
    const { rerender } = render(<PetCard pet={mockPet} {...mockCallbacks} />);

    const image = screen.getByAltText('Fluffy');

    // Simulate image load error
    fireEvent.error(image);

    // After error, image should show placeholder
    expect(image.src).toBe('https://via.placeholder.com/300x200?text=Pet+Image');
  });

  /**
   * Additional test: Should handle missing imageUrl gracefully
   */
  it('Should handle missing imageUrl gracefully', () => {
    const petWithoutImage = { ...mockPet, imageUrl: null };

    render(<PetCard pet={petWithoutImage} {...mockCallbacks} />);

    const image = screen.getByAltText('Fluffy');
    expect(image.src).toBe('https://via.placeholder.com/300x200?text=Pet+Image');
  });

  /**
   * Additional test: Should format currency correctly
   */
  it('Should format price as currency (USD)', () => {
    const petWithDifferentPrice = { ...mockPet, price: 1234.5 };

    render(<PetCard pet={petWithDifferentPrice} {...mockCallbacks} />);

    // Check that price is formatted (exact format depends on Intl.NumberFormat)
    expect(screen.getByText(/\$1,234.5/)).toBeInTheDocument();
  });
});
