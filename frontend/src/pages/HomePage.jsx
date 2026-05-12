import React, { useState, useMemo } from 'react';
import { Container, Box, Typography, Button, Alert, TextField, Slider, Stack } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import PetList from '../components/PetList';
import AddPetModal from '../components/AddPetModal';
import { usePets } from '../hooks/usePets';

/**
 * HomePage component - main page displaying all available pets with filtering.
 */
export default function HomePage() {
  const { pets, loading, error, fetchPets } = usePets();
  const [addModalOpen, setAddModalOpen] = useState(false);
  const [search, setSearch] = useState('');
  const [priceRange, setPriceRange] = useState([0, 1000]);

  // Filtered logic
  const filteredPets = useMemo(() => {
    return pets.filter(pet => {
      const matchesSearch = pet.name.toLowerCase().includes(search.toLowerCase());
      const matchesPrice = pet.price >= priceRange[0] && pet.price <= priceRange[1];
      return matchesSearch && matchesPrice;
    });
  }, [pets, search, priceRange]);

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      {/* Header section */}
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
          sx={{ borderRadius: 8, px: 4, py: 1.5, boxShadow: 4 }}
        >
          Add New Pet
        </Button>
      </Box>

      {/* Filters Section */}
      <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} sx={{ mb: 4, p: 3, bgcolor: 'rgba(255,255,255,0.7)', borderRadius: 4, backdropFilter: 'blur(10px)' }}>
        <TextField 
          label="Search pets..." 
          variant="outlined" 
          size="small"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          fullWidth
        />
        <Box sx={{ width: 200, px: 2 }}>
          <Typography variant="caption">Max Price: ${priceRange[1]}</Typography>
          <Slider
            value={priceRange[1]}
            onChange={(e, val) => setPriceRange([0, val])}
            valueLabelDisplay="auto"
            max={2000}
            size="small"
          />
        </Box>
      </Stack>

      {/* Global Error Display */}
      {error && (
        <Alert severity="error" sx={{ mb: 4 }} action={<Button color="inherit" size="small" onClick={fetchPets}>Retry</Button>}>
          {error}
        </Alert>
      )}

      {/* Main Content: Filtered Pet List */}
      <PetList pets={filteredPets} loading={loading} />

      {/* Add Pet Modal */}
      <AddPetModal 
        open={addModalOpen} 
        onClose={() => setAddModalOpen(false)} 
      />
    </Container>
  );
}
