# Petstore - Full Stack Pet Store Application

> A playful, engaging full-stack pet store application built with modern web technologies.

![Status](https://img.shields.io/badge/Status-Development-blue)
![Java](https://img.shields.io/badge/Java-17+-green)
![Node.js](https://img.shields.io/badge/Node.js-18+-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue)

## 🐾 Project Overview

Petstore is a full-stack web application that allows users to browse, create, edit, and delete pet listings in a visually appealing and playful interface. Built as a school project demonstrating industry best practices, clean code, and comprehensive testing.

## ✨ Features

- **Pet Browsing**: View all available pets with images in a responsive grid layout
- **Add Pet**: Create new pet listings with name, description, price, and image URL
- **Edit Pet**: Modify existing pet information
- **Delete Pet**: Remove pets with confirmation dialog
- **Playful Design**: Material-UI components with engaging visual design for all ages
- **Responsive Layout**: Works seamlessly on desktop, tablet, and mobile devices
- **API Documentation**: Swagger UI for backend REST API exploration

## 🏗️ Tech Stack

### Frontend
- **Vite 5.0+**: Modern build tool with fast ES module optimization
- **React 18+**: Component-based UI library
- **Material-UI (MUI) 5+**: Component library for playful design
- **JavaScript (ES2020+)**: With JSDoc annotations for type safety
- **Axios**: HTTP client for API communication
- **Vitest + React Testing Library**: Testing framework

### Backend
- **Java 17 LTS**: Programming language
- **Spring Boot 3.2+**: Web framework
- **Spring Data JPA**: Object-relational mapping
- **PostgreSQL 14+**: Relational database
- **JUnit 5 + Mockito**: Testing framework

### DevOps
- **Docker & Docker Compose**: Containerization for PostgreSQL
- **Maven**: Backend build tool
- **npm**: Frontend package manager
- **GitHub Actions**: CI/CD pipelines

## 📋 Quick Start

### Prerequisites
- Node.js 18+ LTS
- Java 17+
- Maven 3.8+
- Docker (optional, for PostgreSQL)

### Setup

1. **Clone and navigate**
```bash
git clone <repository-url>
cd petstore
```

2. **Setup environment**
```bash
cp .env.example .env.local
# Update configuration if needed
```

3. **Start PostgreSQL (Docker)**
```bash
docker-compose up -d postgres
```

4. **Setup Backend**
```bash
cd backend
mvn clean install
mvn spring-boot:run
# API available at http://localhost:8080/api
```

5. **Setup Frontend** (in new terminal)
```bash
cd frontend
npm install
npm run dev
# App available at http://localhost:5173
```

## 📁 Project Structure

```
petstore/
├── backend/                   # Spring Boot REST API
│   ├── src/main/java/        # Java source code
│   ├── src/test/java/        # Unit & integration tests
│   ├── src/main/resources/   # Configuration & migrations
│   └── pom.xml               # Maven dependencies
│
├── frontend/                  # Vite + React application
│   ├── src/
│   │   ├── components/       # Reusable components
│   │   ├── pages/            # Page components
│   │   ├── services/         # API client
│   │   ├── hooks/            # Custom hooks
│   │   └── styles/           # Theme & styling
│   ├── src/__tests__/        # Unit tests
│   └── package.json          # npm dependencies
│
├── specs/                     # Project specifications
│   └── 001-petstore-fullstack-app/
│       ├── spec.md           # Feature specification
│       ├── plan.md           # Implementation plan
│       ├── tasks.md          # Task breakdown
│       └── checklists/       # Quality checklists
│
├── docker-compose.yml        # Docker services
└── README.md                 # This file
```

## 🚀 Development Commands

### Backend
```bash
cd backend

# Build
mvn clean package

# Run
mvn spring-boot:run

# Test
mvn test

# Test with coverage
mvn test jacoco:report

# API Documentation
# Available at http://localhost:8080/api/swagger-ui.html
```

### Frontend
```bash
cd frontend

# Development server
npm run dev

# Build for production
npm run build

# Run tests
npm test

# Run tests in watch mode
npm run test:watch

# Lint code
npm run lint

# Format code
npm run format
```

## � Deployment

### Deploy to Render

Petstore is configured for easy deployment to Render.com with Docker containers.

> **Note on Free Tier:** The project is configured for Render's free tier. **Important**: Render's free PostgreSQL database expires after 90 days. If the project is inactive or needs longer-term persistence, you will need to upgrade to a paid plan or migrate the database.

**Quick Deploy (3 steps):**

1. **Push to GitHub**
   ```bash
   git add .
   git commit -m "Ready for Render deployment"
   git push origin main
   ```

2. **Connect to Render**
   - Go to https://dashboard.render.com
   - Click **New +** → **Web Service**
   - Select your GitHub repository
   - Render will auto-detect configuration from `Dockerfile`

3. **Set Environment Variables**
   - Add `PORT=8080` and `SPRING_PROFILES_ACTIVE=prod`
   - Create PostgreSQL database service
   - Database connection variables auto-configured

**Deployment Resources:**
- **Quick Start**: See [RENDER_QUICK_START.md](./RENDER_QUICK_START.md)
- **Detailed Guide**: See [RENDER_DEPLOYMENT_GUIDE.md](./RENDER_DEPLOYMENT_GUIDE.md)
- **Configuration**: See `render.yaml`, `Dockerfile`, `application-prod.properties`

**Features Included:**
- ✅ Multi-stage Docker build (optimized for size)
- ✅ Frontend bundled with backend
- ✅ PostgreSQL managed database
- ✅ Automatic HTTPS
- ✅ Auto-scaling configuration
- ✅ Health checks configured
- ✅ Comprehensive logging

**Pricing:**
- **Free**: 750 hours/month (services spin down after 15 min inactivity)
- **Paid**: $7/month/service (always-on, production-ready)

### Local Production Build

Test production build locally:

```bash
# Build Docker image
docker build -t petstore:latest .

# Run locally
docker run -p 8080:8080 \
  -e DATABASE_URL=postgresql://user:password@host:5432/petstore_db \
  -e SPRING_PROFILES_ACTIVE=prod \
  petstore:latest

# App available at http://localhost:8080
```

## �📊 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/pets` | List all pets |
| GET | `/api/pets/{id}` | Get pet details |
| POST | `/api/pets` | Create new pet |
| PUT | `/api/pets/{id}` | Update pet |
| DELETE | `/api/pets/{id}` | Delete pet |

**Full API docs**: http://localhost:8080/api/swagger-ui.html

## ✅ Quality Standards

- **TDD Mandatory**: Tests written first, implementation follows
- **Code Coverage**: Backend ≥80%, Frontend components tested
- **Clean Code**: Follows language style guides (Google Java, Airbnb JavaScript)
- **Documentation**: Comprehensive Javadoc and JSDoc annotations
- **Responsive Design**: Mobile-first, supports all screen sizes
- **Accessibility**: ARIA attributes, keyboard navigation, screen reader support

## 📝 Contributing

1. Create feature branch: `git checkout -b 001-feature-name`
2. Write tests first (TDD)
3. Implement feature
4. Run quality checks: `npm run lint && npm test` (frontend) or `mvn verify` (backend)
5. Submit PR for code review

## 📚 Documentation

- [Backend README](./backend/README.md) - Backend setup and API details
- [Frontend README](./frontend/README.md) - Frontend setup and component guide
- [Implementation Plan](./specs/001-petstore-fullstack-app/plan.md) - Technical architecture
- [Specification](./specs/001-petstore-fullstack-app/spec.md) - Feature requirements

## 📅 Timeline

- **Duration**: 4-6 weeks
- **Team Size**: 1-3 developers
- **Phases**: 8 implementation phases from setup to integration testing

## 📜 License

School Project - MIT License

## 👥 Team

Petstore Development Team  
*Building a playful, engaging pet store experience for everyone*

---

**Status**: 🚀 In Development  
**Last Updated**: May 2026
