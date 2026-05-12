import React from 'react';
import { 
  Dialog, 
  DialogTitle, 
  DialogContent, 
  IconButton,
  Box
} from '@mui/material';
import { X } from 'lucide-react';
import PetForm from './PetForm';
import { usePets } from '../hooks/usePets';
import { AnimatePresence, motion } from 'framer-motion';

/**
 * Modal dialog for adding a new pet.
 */
export default function AddPetModal({ open, onClose }) {
  const { addPet } = usePets();

  const handleSubmit = async (formData) => {
    const result = await addPet(formData);
    if (result) {
      onClose();
    }
  };

  return (
    <Dialog 
      open={open} 
      onClose={onClose}
      fullWidth
      maxWidth="sm"
      PaperProps={{
        sx: {
          borderRadius: 4,
          padding: 1,
          background: 'rgba(255, 255, 255, 0.9)',
          backdropFilter: 'blur(10px)',
          border: '1px solid rgba(255, 255, 255, 0.3)',
        }
      }}
    >
      <DialogTitle sx={{ m: 0, p: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        Add New Pet
        <IconButton onClick={onClose} size="small">
          <X size={20} />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <PetForm 
          onSubmit={handleSubmit} 
          onCancel={onClose} 
        />
      </DialogContent>
    </Dialog>
  );
}
