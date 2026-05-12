import React, { useState } from 'react';
import {
  Card,
  CardMedia,
  CardContent,
  CardActions,
  Typography,
  Button,
  Box,
  IconButton,
  Tooltip,
  CircularProgress
} from '@mui/material';
import { Eye, Edit3, Trash2, Loader2 } from 'lucide-react';
import { CURRENCY_FORMATTER, UI_CONSTANTS } from '../utils/constants';
import EditPetModal from './EditPetModal';
import DeleteConfirmation from './DeleteConfirmation';
import PetDetailModal from './PetDetailModal';
import { usePets } from '../hooks/usePets';

/**
 * PetCard component for displaying pet summary with modern glassmorphism UI.
 */
export default function PetCard({ pet }) {
  const { removePet } = usePets();
  const [imageError, setImageError] = useState(false);
  const [detailModalOpen, setDetailModalOpen] = useState(false);
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [deleteLoading, setDeleteLoading] = useState(false);

  const handleImageError = () => setImageError(true);

  const handleDelete = async () => {
    setDeleteLoading(true);
    // Optimistic delete handled by hook, but we keep local loading for the button
    await removePet(pet.id);
    setDeleteLoading(false);
    setDeleteDialogOpen(false);
  };

  const imageUrl = imageError || !pet.imageUrl ? UI_CONSTANTS.DEFAULT_IMAGE : pet.imageUrl;
  const isOptimistic = pet.isOptimistic;

  return (
    <Card 
      sx={{ 
        height: '100%', 
        display: 'flex', 
        flexDirection: 'column',
        position: 'relative',
        overflow: 'hidden',
        opacity: isOptimistic ? 0.7 : 1,
      }}
    >
      {/* Optimistic loading overlay */}
      {isOptimistic && (
        <Box sx={{ 
          position: 'absolute', 
          top: 0, left: 0, right: 0, bottom: 0, 
          bgcolor: 'rgba(255,255,255,0.4)', 
          zIndex: 2,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          flexDirection: 'column',
          gap: 1
        }}>
          <Loader2 className="animate-spin text-primary-main" size={32} />
          <Typography variant="caption" sx={{ fontWeight: 'bold' }}>Saving...</Typography>
        </Box>
      )}

      <CardMedia
        component="img"
        height="220"
        image={imageUrl}
        alt={pet.name}
        onError={handleImageError}
        sx={{ 
          objectCover: 'cover',
          transition: 'transform 0.5s ease',
          '&:hover': { transform: 'scale(1.05)' }
        }}
      />
      
      <CardContent sx={{ flexGrow: 1, p: 3 }}>
        <Typography variant="h5" component="h2" sx={{ fontWeight: 'bold', mb: 1 }} noWrap>
          {pet.name}
        </Typography>
        <Typography variant="body2" color="text.secondary" sx={{ 
          mb: 2, 
          height: '2.8em', 
          overflow: 'hidden',
          display: '-webkit-box',
          WebkitLineClamp: 2,
          WebkitBoxOrient: 'vertical'
        }}>
          {pet.description || 'No description available for this pet.'}
        </Typography>
        <Typography variant="h6" color="primary.main" sx={{ fontWeight: 'bold' }}>
          {CURRENCY_FORMATTER.format(pet.price)}
        </Typography>
      </CardContent>

      <CardActions sx={{ p: 2, pt: 0, justifyContent: 'space-between' }}>
        <Button 
          variant="text" 
          startIcon={<Eye size={18} />} 
          onClick={() => setDetailModalOpen(true)}
          disabled={isOptimistic}
        >
          Details
        </Button>
        
        <Box sx={{ display: 'flex', gap: 0.5 }}>
          <Tooltip title="Edit Pet">
            <IconButton 
              size="small" 
              color="primary" 
              onClick={() => setEditModalOpen(true)}
              disabled={isOptimistic}
              sx={{ bgcolor: 'primary.light', '&:hover': { bgcolor: 'primary.main', color: 'white' }, opacity: 0.8 }}
            >
              <Edit3 size={18} />
            </IconButton>
          </Tooltip>
          <Tooltip title="Delete Pet">
            <IconButton 
              size="small" 
              color="error" 
              onClick={() => setDeleteDialogOpen(true)}
              disabled={isOptimistic}
              sx={{ bgcolor: 'error.light', '&:hover': { bgcolor: 'error.main', color: 'white' }, opacity: 0.8 }}
            >
              <Trash2 size={18} />
            </IconButton>
          </Tooltip>
        </Box>
      </CardActions>

      {/* Modals */}
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
    </Card>
  );
}
