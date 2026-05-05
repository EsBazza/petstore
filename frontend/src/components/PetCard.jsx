import React, { useState } from 'react';
import { motion } from 'framer-motion';
import {
  Card,
  CardMedia,
  CardContent,
  CardActions,
  Typography,
  Button,
  Box,
  Snackbar,
  Alert,
  IconButton
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { CURRENCY_FORMATTER } from '../utils/constants';
import EditPetModal from './EditPetModal';
import DeleteConfirmation from './DeleteConfirmation';
import PetDetailModal from './PetDetailModal';
import { usePets } from '../hooks/usePets';

const PLACEHOLDER_IMAGE = 'https://via.placeholder.com/300x200?text=Pet+Image';

export default function PetCard({ pet }) {
  const { removePet } = usePets();
  const [imageError, setImageError] = useState(false);
  const [detailModalOpen, setDetailModalOpen] = useState(false);
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [deleteLoading, setDeleteLoading] = useState(false);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const handleImageError = () => setImageError(true);

  const handleDelete = async () => {
    setDeleteLoading(true);
    const success = await removePet(pet.id);
    setDeleteLoading(false);
    
    if (success) {
      setSnackbar({ open: true, message: 'Pet deleted successfully!', severity: 'success' });
      setDeleteDialogOpen(false);
    } else {
      setSnackbar({ open: true, message: 'Failed to delete pet.', severity: 'error' });
    }
  };

  const handleSnackbarClose = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  const imageUrl = imageError || !pet.imageUrl ? PLACEHOLDER_IMAGE : pet.imageUrl;

  return (
    <motion.div
      whileHover={{ y: -8 }}
      className="h-full"
    >
      <Card className="card-style h-full flex flex-col">
        <CardMedia
          component="img"
          height="200"
          image={imageUrl}
          alt={pet.name}
          onError={handleImageError}
          className="object-cover bg-slate-100"
        />
        <CardContent className="flex-grow">
          <Typography variant="h6" className="font-bold mb-2 truncate">
            {pet.name}
          </Typography>
          <Typography variant="body2" className="text-slate-600 line-clamp-2">
            {pet.description || 'No description provided.'}
          </Typography>
          <Typography variant="h6" className="text-indigo-600 font-bold mt-4">
            {CURRENCY_FORMATTER.format(pet.price)}
          </Typography>
        </CardContent>
        <CardActions className="justify-between px-4 pb-4">
          <Button startIcon={<VisibilityIcon />} onClick={() => setDetailModalOpen(true)}>View</Button>
          <Box>
            <IconButton color="info" onClick={() => setEditModalOpen(true)}><EditIcon /></IconButton>
            <IconButton color="error" onClick={() => setDeleteDialogOpen(true)}><DeleteIcon /></IconButton>
          </Box>
        </CardActions>
      </Card>

      <PetDetailModal 
        pet={pet} 
        open={detailModalOpen} 
        onClose={() => setDetailModalOpen(false)} 
      />

      <EditPetModal 
        pet={pet} 
        open={editModalOpen} 
        onClose={() => setEditModalOpen(false)} 
      />
      
      <DeleteConfirmation 
        open={deleteDialogOpen} 
        petName={pet.name} 
        loading={deleteLoading}
        onClose={() => setDeleteDialogOpen(false)}
        onConfirm={handleDelete}
      />

      <Snackbar 
        open={snackbar.open} 
        autoHideDuration={6000} 
        onClose={handleSnackbarClose}
      >
        <Alert onClose={handleSnackbarClose} severity={snackbar.severity}>{snackbar.message}</Alert>
      </Snackbar>
    </motion.div>
  );
}
