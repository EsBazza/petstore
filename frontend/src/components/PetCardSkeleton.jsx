import React from 'react';
import { Card, CardContent, Box } from '@mui/material';
import Skeleton from 'react-loading-skeleton';
import 'react-loading-skeleton/dist/skeleton.css';

/**
 * Skeleton loader component for the PetCard layout.
 * Ensures perceived performance by mirroring the PetCard structure.
 */
export default function PetCardSkeleton() {
  return (
    <Card className="card-style h-full flex flex-col">
      <Skeleton height={200} />
      <CardContent className="flex-grow">
        <Skeleton height={24} width="80%" className="mb-2" />
        <Skeleton count={2} height={16} className="mb-4" />
        <Box mt={4}>
          <Skeleton height={28} width="40%" />
        </Box>
      </CardContent>
    </Card>
  );
}
