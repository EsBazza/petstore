import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Container,
  Box,
  Typography,
  Button,
  CircularProgress,
  Alert,
  Card,
  CardMedia,
  CardContent,
  Divider,
  Grid
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { usePets } from '../hooks/usePets';
import { CURRENCY_FORMATTER } from '../utils/constants';

/**
 * PetDetailPage component displaying full information about a specific pet.
 * 
 * Features:
 * - Fetches pet details by ID from URL params
 * - Shows loading and error states
 * - Displays image, full name, detailed description, and price
 * - Back button to return to HomePage
 */
const PetDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { fetchPetById, loading, error } = usePets();
  const [pet, setPet] = useState(null);

  useEffect(() => {
    const loadPet = async () => {
      const data = await fetchPetById(id);
      setPet(data);
    };
    loadPet();
  }, [id]);

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh">
        <CircularProgress size={64} />
      </Box>
    );
  }

  if (error || !pet) {
    return (
      <Container maxWidth="sm" sx={{ py: 8 }}>
        <Alert severity="error" sx={{ mb: 2 }}>
          {error || 'Pet not found'}
        </Alert>
        <Button variant="contained" onClick={() => navigate('/')}>
          Back to Browse
        </Button>
      </Container>
    );
  }

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Button 
        startIcon={<ArrowBackIcon />} 
        onClick={() => navigate('/')}
        sx={{ mb: 3 }}
      >
        Back to Browse
      </Button>

      <Card>
        <CardMedia
          component="img"
          height="400"
          image={pet.imageUrl || 'https://via.placeholder.com/800x400?text=No+Image'}
          alt={pet.name}
          sx={{ objectFit: 'cover' }}
        />
        <CardContent sx={{ p: 4 }}>
          <Box display="flex" justifyContent="space-between" alignItems="flex-start" mb={2}>
            <Typography variant="h2" component="h1" gutterBottom sx={{ fontWeight: 'bold' }}>
              {pet.name}
            </Typography>
            <Typography variant="h3" color="primary" sx={{ fontWeight: 'bold' }}>
              {CURRENCY_FORMATTER.format(pet.price)}
            </Typography>
          </Box>
          
          <Divider sx={{ mb: 3 }} />
          
          <Typography variant="h5" color="textSecondary" paragraph>
            {pet.description || 'No detailed description available.'}
          </Typography>
          
          <Box mt={4} sx={{ color: 'text.secondary' }}>
            <Typography variant="body2">Created: {new Date(pet.createdAt).toLocaleDateString()}</Typography>
          </Box>
        </CardContent>
      </Card>
    </Container>
  );
};

export default PetDetailPage;
