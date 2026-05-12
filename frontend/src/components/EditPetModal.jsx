import React from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton
} from '@mui/material';
import { X } from 'lucide-react';
import PetForm from './PetForm';
import { usePets } from '../hooks/usePets';

/**
 * Modal dialog for editing an existing pet.
 */
const EditPetModal = ({ pet, open, onClose }) => {
  const { updatePetData } = usePets();

  const handleSubmit = async (formData) => {
    const result = await updatePetData(pet.id, formData);
    if (result) {
      onClose();
    }
  };

  return (
    <Dialog 
      open={open} 
      onClose={onClose} 
      maxWidth="sm" 
      fullWidth
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
        Edit Pet: {pet?.name}
        <IconButton onClick={onClose} size="small">
          <X size={20} />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <PetForm 
          initialData={pet}
          onSubmit={handleSubmit} 
          onCancel={onClose} 
        />
      </DialogContent>
    </Dialog>
  );
};

export default EditPetModal;
