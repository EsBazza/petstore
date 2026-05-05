# Petstore Frontend

Vite + React frontend application for Petstore with Material-UI components and playful design.

## Technology Stack

- **Build Tool**: Vite 5.0+
- **UI Library**: React 18+
- **Component Library**: Material-UI (MUI) 5+
- **Language**: JavaScript (ES2020+) with JSDoc annotations
- **Testing**: Vitest + React Testing Library
- **HTTP Client**: Axios

## Prerequisites

- Node.js 18+ LTS
- npm or yarn package manager

## Setup & Development

### 1. Install Dependencies
```bash
npm install
```

### 2. Create Environment File
```bash
cp .env.example .env.local
# Update API_BASE_URL if backend is on different host
```

### 3. Start Development Server
```bash
npm run dev
```

Development server opens at `http://localhost:5173`

### 4. Build for Production
```bash
npm run build
```

Output is in the `dist/` directory.

## Testing

### Run All Tests
```bash
npm test
```

### Run Tests in Watch Mode
```bash
npm run test:watch
```

### Run Tests with Coverage Report
```bash
npm run test:coverage
```

## Code Quality

### Lint Code
```bash
npm run lint
```

### Format Code
```bash
npm run format
```

## Project Structure

```
frontend/
├── src/
│   ├── main.jsx              # React entry point
│   ├── App.jsx               # Root component
│   ├── components/           # Reusable React components
│   │   ├── PetList.jsx
│   │   ├── PetCard.jsx
│   │   ├── PetDetail.jsx
│   │   ├── PetForm.jsx
│   │   ├── DeleteConfirmation.jsx
│   │   └── ErrorBoundary.jsx
│   ├── pages/                # Page components
│   │   ├── HomePage.jsx
│   │   └── PetDetailPage.jsx
│   ├── services/             # API and utility services
│   │   └── api.js            # Axios API client
│   ├── hooks/                # Custom React hooks
│   │   └── usePets.js
│   ├── styles/               # Styling and themes
│   │   └── theme.js
│   ├── utils/                # Utility functions
│   │   └── constants.js
│   └── __tests__/            # Test files
│       ├── components/
│       ├── hooks/
│       └── services/
├── public/                   # Static assets
├── index.html               # HTML entry point
├── vite.config.js           # Vite configuration
├── vitest.config.js         # Vitest configuration
└── package.json             # Dependencies
```

## API Integration

The frontend communicates with the backend API at `http://localhost:8080/api` (configurable via `.env.local`).

### Main Endpoints

- `GET /api/pets` - List all pets
- `GET /api/pets/:id` - Get pet details
- `POST /api/pets` - Create new pet
- `PUT /api/pets/:id` - Update pet
- `DELETE /api/pets/:id` - Delete pet

## Code Quality Standards

- Follow Airbnb JavaScript style guide
- Document functions with JSDoc annotations
- Write tests for components and services
- Keep components small and focused
- Use custom hooks for shared logic
- Responsive design for mobile, tablet, desktop

## Contributing

1. Create feature branch: `git checkout -b feature/your-feature`
2. Write tests first (TDD)
3. Implement feature
4. Run `npm run lint && npm run format && npm test`
5. Submit PR for code review

## License

School Project - MIT License
