import React from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button,
  CircularProgress
} from '@mui/material';

/**
 * Confirmation dialog for deleting a pet.
 * 
 * @param {Object} props
 * @param {boolean} props.open - Dialog open state
 * @param {string} props.petName - Name of the pet to delete
 * @param {Function} props.onClose - Dialog close handler
 * @param {Function} props.onConfirm - Delete confirmation handler
 * @param {boolean} props.loading - Loading state of the deletion
 */
const DeleteConfirmation = ({ open, petName, onClose, onConfirm, loading }) => {
  return (
    <Dialog
      open={open}
      onClose={onClose}
      aria-labelledby="delete-dialog-title"
      aria-describedby="delete-dialog-description"
    >
      <DialogTitle id="delete-dialog-title">
        Confirm Deletion
      </DialogTitle>
      <DialogContent>
        <DialogContentText id="delete-dialog-description">
          Are you sure you want to delete <strong>{petName}</strong>? This action cannot be undone.
        </DialogContentText>
      </DialogContent>
      <DialogActions sx={{ px: 3, pb: 2 }}>
        <Button onClick={onClose} disabled={loading} variant="outlined">
          Cancel
        </Button>
        <Button 
          onClick={onConfirm} 
          color="error" 
          variant="contained" 
          disabled={loading}
          autoFocus
          sx={{ minWidth: 100 }}
        >
          {loading ? <CircularProgress size={24} color="inherit" /> : 'Delete'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default DeleteConfirmation;
