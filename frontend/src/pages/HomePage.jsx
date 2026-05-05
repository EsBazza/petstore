import React, { useState } from 'react';
import { Container, Box, Typography, Button, Alert } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import PetList from '../components/PetList';
import AddPetModal from '../components/AddPetModal';
import { usePets } from '../hooks/usePets';

/**
 * HomePage component - main page displaying all available pets
 * 
 * Features:
 * - Header with title and "Add New Pet" button
 * - PetList component for browsing
 * - AddPetModal for creating new listings
 * - Responsive layout
 * 
 * @component
 * @returns {React.ReactElement} Home page with pet management interface
 */
export default function HomePage() {
  const { pets, loading, error, fetchPets } = usePets();
  const [addModalOpen, setAddModalOpen] = useState(false);

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      {/* Header section with Title and Add Button */}
      <Box sx={{ 
        display: 'flex', 
        justifyContent: 'space-between', 
        alignItems: 'center', 
        mb: 4,
        flexDirection: { xs: 'column', sm: 'row' },
        gap: 2
      }}>
        <Box>
          <Typography variant="h3" component="h1" gutterBottom color="primary" sx={{ fontWeight: 'bold' }}>
            🐾 Petstore
          </Typography>
          <Typography variant="subtitle1" color="textSecondary">
            Find your perfect companion today!
          </Typography>
        </Box>
        
        <Button
          variant="contained"
          size="large"
          startIcon={<AddIcon />}
          onClick={() => setAddModalOpen(true)}
          sx={{ 
            borderRadius: 8,
            px: 4,
            py: 1.5,
            boxShadow: 4
          }}
        >
          Add New Pet
        </Button>
      </Box>

      {/* Global Error Display */}
      {error && (
        <Alert 
          severity="error" 
          sx={{ mb: 4 }} 
          action={
            <Button color="inherit" size="small" onClick={fetchPets}>
              Retry
            </Button>
          }
        >
          {error}
        </Alert>
      )}

      {/* Main Content: Pet List */}
      <PetList pets={pets} loading={loading} />

      {/* Add Pet Modal */}
      <AddPetModal 
        open={addModalOpen} 
        onClose={() => setAddModalOpen(false)} 
      />
    </Container>
  );
}
