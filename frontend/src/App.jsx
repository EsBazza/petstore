import { Toaster } from 'sonner'
import './index.css'
import { ThemeProvider } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import theme from './styles/theme'
import HomePage from './pages/HomePage'
import PetDetailPage from './pages/PetDetailPage'

import { PetProvider } from './context/PetContext'

/**
 * Root App component.
 * Sets up Material-UI theming, routing, and main layout.
 */
function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <PetProvider>
        <Toaster position="top-center" richColors />
        <Router>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/pets/:id" element={<PetDetailPage />} />
          </Routes>
        </Router>
      </PetProvider>
    </ThemeProvider>
  )
}

export default App
