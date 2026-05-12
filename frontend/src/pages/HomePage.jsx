import React, { useState, useMemo } from 'react';
import { Container, Box, Typography, Button, Alert, TextField, Stack, MenuItem, Select, FormControl, InputLabel } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import PetList from '../components/PetList';
import AddPetModal from '../components/AddPetModal';
import { usePets } from '../hooks/usePets';

/**
 * HomePage component - main page displaying all available pets with simple sorting.
 */
export default function HomePage() {
  const { pets, loading, error, fetchPets } = usePets();
  const [addModalOpen, setAddModalOpen] = useState(false);
  const [search, setSearch] = useState('');
  const [sort, setSort] = useState('newest'); // Options: newest, name-asc, price-asc

  // Filtered and Sorted logic
  const filteredPets = useMemo(() => {
    let result = pets.filter(pet => 
      pet.name.toLowerCase().includes(search.toLowerCase())
    );

    return result.sort((a, b) => {
      if (sort === 'name-asc') return a.name.localeCompare(b.name);
      if (sort === 'price-asc') return a.price - b.price;
      return new Date(b.createdAt) - new Date(a.createdAt); // newest
    });
  }, [pets, search, sort]);

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

      {/* Controls Section */}
      <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} sx={{ mb: 4, p: 3, bgcolor: 'rgba(255,255,255,0.7)', borderRadius: 4, backdropFilter: 'blur(10px)' }}>
        <TextField 
          label="Search pets..." 
          variant="outlined" 
          size="small"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          fullWidth
        />
        <FormControl size="small" sx={{ minWidth: 200 }}>
          <InputLabel>Sort By</InputLabel>
          <Select
            value={sort}
            label="Sort By"
            onChange={(e) => setSort(e.target.value)}
          >
            <MenuItem value="newest">Newest</MenuItem>
            <MenuItem value="name-asc">Name (A-Z)</MenuItem>
            <MenuItem value="price-asc">Price (Lowest to Highest)</MenuItem>
          </Select>
        </FormControl>
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
