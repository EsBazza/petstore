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
  IconButton,
  Stack,
  Paper,
  Grid
} from '@mui/material';
import { ArrowLeft, Heart, Share2, DollarSign, Calendar } from 'lucide-react';
import { motion } from 'framer-motion';
import { usePets } from '../hooks/usePets';
import { CURRENCY_FORMATTER } from '../utils/constants';

const PetDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { fetchPetById, loading, error, pets } = usePets();
  const [pet, setPet] = useState(null);

  useEffect(() => {
    const loadPet = async () => {
      const data = await fetchPetById(id);
      setPet(data);
    };
    loadPet();
  }, [id]);

  if (loading) {
    return <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh"><CircularProgress /></Box>;
  }

  if (error || !pet) {
    return (
      <Container maxWidth="sm" sx={{ py: 8 }}>
        <Alert severity="error" sx={{ mb: 2 }}>{error || 'Pet not found'}</Alert>
        <Button variant="contained" onClick={() => navigate('/')}>Back to Browse</Button>
      </Container>
    );
  }

  // Find related pets
  const relatedPets = pets.filter(p => p.id !== pet.id && Math.abs(p.price - pet.price) < 50).slice(0, 3);

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Button startIcon={<ArrowLeft />} onClick={() => navigate('/')} sx={{ mb: 3 }}>Back to Browse</Button>

      <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.5 }}>
        <Card sx={{ 
          display: 'flex', 
          flexDirection: { xs: 'column', md: 'row' },
          borderRadius: 6,
          background: 'rgba(255, 255, 255, 0.8)',
          backdropFilter: 'blur(15px)',
          border: '1px solid rgba(255, 255, 255, 0.3)',
          boxShadow: '0 8px 32px rgba(0,0,0,0.1)'
        }}>
          <CardMedia component="img" sx={{ width: { md: '50%' }, height: 400 }} image={pet.imageUrl} alt={pet.name} />
          <CardContent sx={{ p: 5, flex: 1 }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
              <Typography variant="h2" sx={{ fontWeight: 800 }}>{pet.name}</Typography>
              <Stack direction="row" spacing={1}>
                <IconButton><Heart /></IconButton>
                <IconButton><Share2 /></IconButton>
              </Stack>
            </Box>
            <Typography variant="h4" color="primary" sx={{ fontWeight: 'bold', mb: 3 }}>
              {CURRENCY_FORMATTER.format(pet.price)}
            </Typography>
            <Divider sx={{ mb: 3 }} />
            <Typography variant="h6" color="textSecondary" paragraph>{pet.description}</Typography>
            <Box display="flex" alignItems="center" mt={4} sx={{ color: 'text.secondary' }}>
              <Calendar size={18} style={{ marginRight: 8 }} />
              <Typography variant="body2">Added on: {new Date(pet.createdAt).toLocaleDateString()}</Typography>
            </Box>
          </CardContent>
        </Card>
      </motion.div>

      {/* Related Pets Section */}
      {relatedPets.length > 0 && (
        <Box sx={{ mt: 8 }}>
          <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 4 }}>You might also like</Typography>
          <Grid container spacing={3}>
            {relatedPets.map(rp => (
              <Grid item key={rp.id} xs={12} sm={4}>
                <Paper sx={{ p: 2, borderRadius: 4, textAlign: 'center', cursor: 'pointer' }} onClick={() => navigate(`/pets/${rp.id}`)}>
                  <Typography variant="h6">{rp.name}</Typography>
                  <Typography color="primary">{CURRENCY_FORMATTER.format(rp.price)}</Typography>
                </Paper>
              </Grid>
            ))}
          </Grid>
        </Box>
      )}
    </Container>
  );
};

export default PetDetailPage;
