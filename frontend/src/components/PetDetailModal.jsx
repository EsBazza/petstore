import React from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import {
  Dialog,
  DialogContent,
  IconButton,
  Typography,
  Box,
  Divider,
  Chip
} from '@mui/material';
import { X, DollarSign, Calendar, Heart } from 'lucide-react';
import { CURRENCY_FORMATTER } from '../utils/constants';

const PetDetailModal = ({ pet, open, onClose }) => {
  if (!pet) return null;

  return (
    <Dialog 
      open={open} 
      onClose={onClose} 
      maxWidth="md" 
      fullWidth
      PaperProps={{
        className: 'rounded-3xl shadow-2xl overflow-hidden'
      }}
    >
      <DialogContent className="p-0 relative">
        <IconButton 
          onClick={onClose} 
          className="absolute top-4 right-4 z-10 bg-white/80 hover:bg-white"
        >
          <X className="w-6 h-6 text-slate-800" />
        </IconButton>

        <Box className="grid md:grid-cols-2 gap-0">
          <motion.img 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            src={pet.imageUrl || 'https://via.placeholder.com/600x600?text=Pet+Image'} 
            alt={pet.name}
            className="w-full h-80 md:h-full object-cover"
          />
          
          <Box className="p-8 bg-slate-50 flex flex-col justify-between">
            <Box>
              <Typography variant="h3" className="font-extrabold text-slate-900 mb-2">
                {pet.name}
              </Typography>
              <Box className="flex items-center gap-2 mb-6">
                <Chip icon={<DollarSign size={16}/>} label={CURRENCY_FORMATTER.format(pet.price)} color="primary" className="rounded-full" />
                <Chip icon={<Calendar size={16}/>} label={`Listed: ${new Date(pet.createdAt).toLocaleDateString()}`} variant="outlined" className="rounded-full" />
              </Box>
              <Typography variant="body1" className="text-slate-600 leading-relaxed mb-6">
                {pet.description || 'No description provided.'}
              </Typography>
            </Box>

            <Box className="flex items-center gap-4">
              <button className="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 rounded-full transition-all">
                Adopt Me!
              </button>
              <IconButton className="bg-rose-100 hover:bg-rose-200 text-rose-500 p-3">
                <Heart size={24} />
              </IconButton>
            </Box>
          </Box>
        </Box>
      </DialogContent>
    </Dialog>
  );
};

export default PetDetailModal;
