import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
  Snackbar,
  Alert
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import PetForm from './PetForm';
import { usePets } from '../hooks/usePets';

/**
 * Modal dialog for editing an existing pet.
 * 
 * @param {Object} props
 * @param {Object} props.pet - Pet data to edit
 * @param {boolean} props.open - Modal open state
 * @param {Function} props.onClose - Modal close handler
 */
const EditPetModal = ({ pet, open, onClose }) => {
  const { updatePetData } = usePets();
  const [loading, setLoading] = useState(false);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const handleSubmit = async (formData) => {
    setLoading(true);
    const result = await updatePetData(pet.id, formData);
    setLoading(false);
    
    if (result) {
      setSnackbar({ open: true, message: 'Pet updated successfully!', severity: 'success' });
      onClose();
    } else {
      setSnackbar({ open: true, message: 'Failed to update pet. Please try again.', severity: 'error' });
    }
  };

  const handleSnackbarClose = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle sx={{ m: 0, p: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          Edit Pet: {pet?.name}
          <IconButton onClick={onClose}>
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent dividers>
          <PetForm 
            initialData={pet}
            onSubmit={handleSubmit} 
            onCancel={onClose} 
            loading={loading} 
          />
        </DialogContent>
      </Dialog>

      <Snackbar 
        open={snackbar.open} 
        autoHideDuration={6000} 
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={handleSnackbarClose} severity={snackbar.severity} sx={{ width: '100%' }}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  );
};

export default EditPetModal;
