import React from 'react';
import { Box, Grid, Typography } from '@mui/material';
import { motion, AnimatePresence } from 'framer-motion';
import PetCard from './PetCard';
import PetCardSkeleton from './PetCardSkeleton';

const container = {
  hidden: { opacity: 0 },
  show: {
    opacity: 1,
    transition: {
      staggerChildren: 0.1
    }
  }
};

const item = {
  hidden: { opacity: 0, y: 20 },
  show: { opacity: 1, y: 0 }
};

/**
 * PetList component displays a grid of all available pets with staggered animations.
 */
export default function PetList({ pets, loading }) {
  // Loading state: Show grid of skeletons
  if (loading) {
    return (
      <Box sx={{ py: 2 }}>
        <Grid container spacing={4}>
          {[...Array(8)].map((_, i) => (
            <Grid item xs={12} sm={6} md={4} lg={3} key={i}>
              <PetCardSkeleton />
            </Grid>
          ))}
        </Grid>
      </Box>
    );
  }

  // Empty state
  if (!loading && (!pets || pets.length === 0)) {
    return (
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.5 }}
      >
        <Box sx={{ py: 8, textAlign: 'center' }}>
          <Typography variant="h4" gutterBottom color="textSecondary" sx={{ fontWeight: 600 }}>
            🐾 The petstore is empty
          </Typography>
          <Typography variant="body1" color="textSecondary">
            Click "Add New Pet" to bring some friends here!
          </Typography>
        </Box>
      </motion.div>
    );
  }

  return (
    <Box sx={{ py: 2 }}>
      <Grid 
        container 
        spacing={4} 
        component={motion.div}
        variants={container}
        initial="hidden"
        animate="show"
      >
        <AnimatePresence mode="popLayout">
          {pets.map((pet) => (
            <Grid 
              item 
              xs={12} 
              sm={6} 
              md={4} 
              lg={3} 
              key={pet.id}
              component={motion.div}
              variants={item}
              layout
              exit={{ opacity: 0, scale: 0.8 }}
            >
              <PetCard pet={pet} />
            </Grid>
          ))}
        </AnimatePresence>
      </Grid>
    </Box>
  );
}
