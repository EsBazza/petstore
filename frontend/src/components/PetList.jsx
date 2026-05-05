import React from 'react';
import { Box, Grid, Typography, Alert } from '@mui/material';
import PetCard from './PetCard';
import PetCardSkeleton from './PetCardSkeleton';

/**
 * PetList component displays a grid of all available pets.
 */
export default function PetList({ pets, loading }) {
  // Loading state: Show grid of skeletons
  if (loading) {
    return (
      <Box sx={{ py: 2 }}>
        <Grid container spacing={3}>
          {[...Array(8)].map((_, i) => (
            <Grid item xs={12} sm={6} md={4} lg={3} key={i}>
              <PetCardSkeleton />
            </Grid>
          ))}
        </Grid>
      </Box>
    );
  }

  // Empty state
  if (!loading && (!pets || pets.length === 0)) {
    return (
      <Box sx={{ py: 8, textAlign: 'center' }}>
        <Typography variant="h6" color="textSecondary" gutterBottom>
          No pets available
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Add one to get started!
        </Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ py: 2 }}>
      <Grid container spacing={3}>
        {pets.map((pet) => (
          <Grid item xs={12} sm={6} md={4} lg={3} key={pet.id}>
            <PetCard pet={pet} />
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
