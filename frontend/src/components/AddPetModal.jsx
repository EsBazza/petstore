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
 * Modal dialog for adding a new pet.
 * 
 * @param {Object} props
 * @param {boolean} props.open - Modal open state
 * @param {Function} props.onClose - Modal close handler
 */
const AddPetModal = ({ open, onClose }) => {
  const { addPet } = usePets();
  const [loading, setLoading] = useState(false);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const handleSubmit = async (formData) => {
    setLoading(true);
    const result = await addPet(formData);
    setLoading(false);
    
    if (result) {
      setSnackbar({ open: true, message: 'Pet added successfully!', severity: 'success' });
      onClose(); // Close modal on success
    } else {
      setSnackbar({ open: true, message: 'Failed to add pet. Please try again.', severity: 'error' });
    }
  };

  const handleSnackbarClose = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle sx={{ m: 0, p: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          Add New Pet listing
          <IconButton onClick={onClose}>
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent dividers>
          <PetForm 
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

export default AddPetModal;
