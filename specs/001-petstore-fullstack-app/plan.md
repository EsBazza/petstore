# Implementation Plan: Petstore Fullstack Application

**Branch**: `001-petstore-fullstack` | **Date**: 2026-05-05 | **Spec**: [spec.md](spec.md)
**Input**: Feature specification from `/specs/001-petstore-fullstack-app/spec.md`

## Summary

Build a full-stack pet store web application that allows users to browse available pets with images and perform complete CRUD operations (Create, Read, Update, Delete). The application features a playful, responsive UI designed for users of all ages. The technical approach uses Vite-React frontend with JavaScript/JSDoc for rapid development, Spring Boot 3.2+ REST API backend, and PostgreSQL database, all following industry best practices and strict TDD discipline.

**Primary Value Deliverables**:
1. **Pet Browsing** - Users can view all pets with images, name, description, and price
2. **Add Pet** - Users can create new pet listings directly from the UI
3. **Edit Pet** - Users can modify existing pet information
4. **Delete Pet** - Users can remove pet listings with confirmation
5. **Playful UX** - Material-UI components create engaging, responsive interface for all ages

## Technical Context

**Language/Version**:
- **Frontend**: JavaScript (ES2020+) with JSDoc annotations for type safety
- **Backend**: Java 17 LTS or later
- **Database**: PostgreSQL 14+

**Primary Dependencies**:
- **Frontend**: Vite 5.0+, React 18+, Material-UI (MUI) v5+, Vitest, React Testing Library, Axios
- **Backend**: Spring Boot 3.2+, Spring Data JPA, JUnit 5, Mockito, Lombok, Spring Doc OpenAPI
- **Build**: Maven 3.8+ (backend), Node.js 18+ LTS (frontend)

**Storage**: PostgreSQL 14+ with normalized schema, HikariCP connection pooling, Flyway/Liquibase migrations

**Testing**: 
- Frontend: Vitest + React Testing Library (component tests), E2E tests for critical flows
- Backend: JUnit 5 + Mockito (unit tests), integration tests with TestContainers, API contract tests
- Required Coverage: Backend ≥80%, Frontend component interactions

**Target Platform**: 
- Web browser (Chrome, Firefox, Safari, Edge - latest 2 versions)
- Desktop, tablet, mobile responsive layouts
- Linux/macOS development environments; deployment on cloud or local servers

**Project Type**: Full-stack web application (frontend SPA + REST API backend + relational database)

**Performance Goals**:
- Page load within 3 seconds (SC-001)
- CRUD operations complete within 2 seconds excluding network latency (SC-007)
- Support <10,000 pets in MVP (no pagination initially required)

**Constraints**:
- No authentication/authorization required for MVP
- No file uploads; images referenced by URL only
- No pagination or advanced filtering for MVP
- No offline capabilities required
- Development/staging environment only (no production deployment requirements)

**Scale/Scope**:
- MVP scope: ~2-3 frontend screens (pet list, detail view, add/edit form)
- Backend: 6-8 REST endpoints (CRUD operations + list/filter)
- Database: 1 primary entity (Pet) with ~2000-5000 test records
- Expected code: 2000-3000 LOC frontend, 1500-2000 LOC backend
- Team: 1-3 developers; school project timeline

## Constitution Check

**GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.**

### Principle I: Feature-First Development ✅ PASS
- ✅ All 5 user stories have clear acceptance criteria (spec.md)
- ✅ Each story is independently testable and deployable
- ✅ CRUD operations (Add, Edit, Delete) are standalone features
- ✅ Pet browsing is the MVP foundation

### Principle II: Clean Code & Industry Best Practices ✅ PASS
- ✅ Frontend: JavaScript following Airbnb style + JSDoc conventions
- ✅ Backend: Java following Google Java Style Guide
- ✅ Database: Normalized schema with clear naming conventions
- ✅ PR-based workflow with code review requirements

### Principle III: Test-Driven Development ✅ PASS
- ✅ TDD mandatory for all features (constitution requirement)
- ✅ Backend test coverage ≥80% specified (SC-010)
- ✅ Frontend component testing with Vitest + React Testing Library
- ✅ E2E tests for critical user journeys (pet CRUD flows)
- ✅ Integration tests for API ↔ database interactions

### Principle IV: Responsive & Accessible Design ✅ PASS
- ✅ Responsive design required (SC-005): desktop/tablet/mobile
- ✅ Material-UI (MUI) mandatory for consistency and accessibility
- ✅ Playful design for all ages (FR-014)
- ✅ Accessibility standards: ARIA attributes, keyboard navigation

### Principle V: Cross-Stack Quality Assurance ✅ PASS
- ✅ Frontend gate: Component tests + responsive testing + accessibility audits
- ✅ Backend gate: Unit + integration + API contract tests
- ✅ Database gate: Schema validation + constraint enforcement + migration tests
- ✅ No layer complete until downstream dependencies pass

**Gate Status**: ✅ **ALL PRINCIPLES SATISFIED** - Ready for Phase 0 Research

## Project Structure

### Documentation (this feature)

```text
specs/001-petstore-fullstack-app/
├── plan.md              # This file (implementation architecture & design)
├── spec.md              # Feature specification with user stories & requirements
├── research.md          # Phase 0: Technology research & dependency analysis (TBD)
├── data-model.md        # Phase 1: Entity models, schema design, relationships (TBD)
├── quickstart.md        # Phase 1: Setup & development environment guide (TBD)
├── contracts/           # Phase 1: API endpoints, request/response schemas (TBD)
│   ├── pet-api.md       #   - Pet CRUD endpoints specification
│   └── error-responses.md  #   - Standard error response format
└── checklists/
    └── requirements.md  # Quality checklist for specification validation
```

### Source Code (repository root)

```text
petstore/
├── README.md            # Project overview, setup, build, test instructions
├── .github/
│   ├── workflows/       # CI/CD pipeline configurations
│   └── copilot-instructions.md  # Agent context & development guidance
│
├── backend/             # Spring Boot REST API
│   ├── pom.xml          # Maven dependencies and build configuration
│   ├── src/main/java/com/petstore/
│   │   ├── PetstoreApplication.java      # Spring Boot entry point
│   │   ├── config/                       # Configuration classes
│   │   ├── controller/                   # REST endpoint controllers
│   │   │   └── PetController.java        # Pet CRUD endpoints
│   │   ├── service/                      # Business logic services
│   │   │   └── PetService.java           # Pet operations
│   │   ├── repository/                   # Data access (Spring Data JPA)
│   │   │   └── PetRepository.java        # Pet database queries
│   │   ├── entity/                       # JPA entities
│   │   │   └── Pet.java                  # Pet domain model
│   │   ├── dto/                          # Data transfer objects
│   │   │   ├── PetCreateRequest.java     # Create pet request DTO
│   │   │   ├── PetUpdateRequest.java     # Update pet request DTO
│   │   │   └── PetResponse.java          # Pet response DTO
│   │   └── exception/                    # Custom exceptions
│   │       └── PetNotFoundException.java
│   │
│   ├── src/main/resources/
│   │   ├── application.properties        # Spring Boot configuration
│   │   └── db/migration/                 # Flyway database migrations
│   │       └── V1__initial_schema.sql    # Initial Pet table
│   │
│   └── src/test/java/com/petstore/
│       ├── controller/
│       │   └── PetControllerTest.java    # API endpoint tests
│       ├── service/
│       │   └── PetServiceTest.java       # Business logic tests
│       └── repository/
│           └── PetRepositoryTest.java    # Data access tests
│
├── frontend/            # Vite + React application
│   ├── package.json     # Node.js dependencies
│   ├── vite.config.js   # Vite build configuration
│   ├── vitest.config.js # Vitest test configuration
│   ├── .eslintrc.json   # ESLint rules
│   ├── .prettierrc       # Prettier formatting
│   ├── index.html       # HTML entry point
│   │
│   ├── src/
│   │   ├── main.jsx     # React entry point
│   │   ├── App.jsx      # Root component
│   │   │
│   │   ├── components/
│   │   │   ├── PetList.jsx              # Displays all pets in grid
│   │   │   ├── PetCard.jsx              # Individual pet card component
│   │   │   ├── PetDetail.jsx            # Pet details view
│   │   │   ├── PetForm.jsx              # Form for add/edit pet
│   │   │   ├── DeleteConfirmation.jsx   # Confirmation dialog
│   │   │   └── ErrorBoundary.jsx        # Error handling wrapper
│   │   │
│   │   ├── pages/
│   │   │   ├── HomePage.jsx             # Main pet list page
│   │   │   └── PetDetailPage.jsx        # Pet detail page
│   │   │
│   │   ├── services/
│   │   │   └── api.js                   # API client using Axios
│   │   │
│   │   ├── hooks/
│   │   │   └── usePets.js               # Custom hook for pet data
│   │   │
│   │   ├── styles/
│   │   │   └── theme.js                 # MUI theme configuration
│   │   │
│   │   └── utils/
│   │       └── constants.js             # API endpoints, constants
│   │
│   └── src/__tests__/
│       ├── components/
│       │   ├── PetList.test.jsx
│       │   ├── PetCard.test.jsx
│       │   └── PetForm.test.jsx
│       ├── hooks/
│       │   └── usePets.test.js
│       └── services/
│           └── api.test.js
│
├── docker-compose.yml   # Local PostgreSQL + optional backend container
├── .gitignore
└── .env.example         # Environment variables template
```

## Architecture Overview

### Three-Layer Architecture

```
┌─────────────────────────────────────────────┐
│   FRONTEND LAYER (Vite + React)             │
│   ├─ PetList → displays all pets            │
│   ├─ PetForm → add/edit pet modal           │
│   ├─ PetCard → individual pet tiles         │
│   └─ API Client (Axios) → calls backend     │
└──────────────┬──────────────────────────────┘
               │ HTTP/REST
               ↓
┌─────────────────────────────────────────────┐
│   API LAYER (Spring Boot REST)              │
│   ├─ PetController → route requests         │
│   ├─ PetService → business logic            │
│   └─ PetRepository → JPA queries            │
└──────────────┬──────────────────────────────┘
               │ SQL
               ↓
┌─────────────────────────────────────────────┐
│   DATABASE LAYER (PostgreSQL)               │
│   └─ Pet table → persistent storage         │
└─────────────────────────────────────────────┘
```

### Key Data Flow

**Pet Browsing (Read)**:
1. Frontend: `PetList` component mounts → calls `usePets()` hook
2. Hook: Calls `api.getPets()` → GET `/api/pets`
3. Backend: `PetController.getAllPets()` → `PetService.getAllPets()` → `PetRepository.findAll()`
4. Database: Returns all Pet records
5. Frontend: Updates component state → renders grid with images

**Add Pet (Create)**:
1. Frontend: User fills form → clicks "Save"
2. Frontend: Calls `api.createPet(petData)` → POST `/api/pets`
3. Backend: `PetController.createPet()` → validates DTO → `PetService.createPet()`
4. Backend: Saves Pet entity to database
5. Database: Inserts row, returns generated ID
6. Frontend: Receives response → adds to local state → re-renders list

**Edit Pet (Update)**:
1. Frontend: User clicks "Edit" → form pre-populated → modifies fields
2. Frontend: Calls `api.updatePet(id, petData)` → PUT `/api/pets/{id}`
3. Backend: `PetController.updatePet()` → `PetService.updatePet()`
4. Backend: Loads existing Pet → updates fields → saves
5. Frontend: Receives response → updates local state → re-renders

**Delete Pet (Delete)**:
1. Frontend: User clicks "Delete" → confirms dialog
2. Frontend: Calls `api.deletePet(id)` → DELETE `/api/pets/{id}`
3. Backend: `PetController.deletePet()` → `PetService.deletePet()`
4. Backend: Deletes Pet record from database
5. Frontend: Receives success → removes from local state → re-renders list

## Design Principles & Patterns

### Frontend Design Patterns

**State Management**:
- Component-level state with `useState` for form inputs, UI toggles
- Custom hook `usePets` for centralized pet data fetching and caching
- Context API (optional) if state sharing between distant components needed

**Component Architecture**:
- Container/Presentational pattern: Smart components (pages) manage logic, dumb components (cards) display data
- Composition over inheritance: Reusable, single-responsibility components
- Material-UI theming for consistent styling and playful visual design

**Error Handling**:
- Axios interceptors for global error handling
- User-friendly error messages displayed in snackbars or dialogs
- ErrorBoundary component catches React errors and displays fallback UI

### Backend Design Patterns

**Layered Architecture**:
- Controllers: HTTP request handling and routing
- Services: Business logic, validation, orchestration
- Repositories: Data access abstraction (Spring Data JPA)

**Error Handling**:
- Custom exceptions for domain-specific errors (e.g., `PetNotFoundException`)
- Global exception handler with `@ControllerAdvice` for consistent error responses
- HTTP status codes follow REST conventions (200, 201, 400, 404, 500)

**Database Design**:
- Normalized schema with proper foreign keys and constraints
- Timestamps (createdAt, updatedAt) for audit trail
- Indexes on frequently queried columns (id, name)

## Development Workflow

### Phase 0: Research & Dependency Analysis (Parallel execution)

**Research Tasks**:
1. Vite 5.0+ build configuration best practices for React
2. MUI v5+ theming and responsive design patterns
3. Spring Boot 3.2+ REST API best practices
4. PostgreSQL schema design for pet data
5. Testing strategy: Vitest + React Testing Library + JUnit 5 + Mockito
6. Docker setup for local PostgreSQL development
7. ESLint/JSDoc configuration for JavaScript linting and type checking
8. CI/CD pipeline setup with GitHub Actions

**Outputs**: `research.md` with findings, recommended libraries, configuration templates

### Phase 1: Design Artifacts (Sequential after research complete)

**Design Tasks**:
1. **Data Model** (`data-model.md`):
   - Pet entity attributes with validation rules
   - Database schema with SQL DDL
   - Entity relationships and constraints

2. **API Contracts** (`contracts/pet-api.md`):
   - GET /api/pets → list all pets with pagination
   - POST /api/pets → create new pet
   - PUT /api/pets/{id} → update pet
   - DELETE /api/pets/{id} → delete pet
   - GET /api/pets/{id} → get single pet

3. **Error Responses** (`contracts/error-responses.md`):
   - Standard error response format
   - Common error codes and messages
   - Validation error structure

4. **Quickstart** (`quickstart.md`):
   - Development environment setup (Node.js, Java, PostgreSQL)
   - Frontend project initialization and commands
   - Backend project initialization and commands
   - Database setup and migration running
   - Local testing and development workflow

### Phase 2: Task Generation & Implementation (After design approval)

**Task Generation**: `/speckit.tasks` creates ordered, dependency-aware implementation tasks organized by user story:
- Setup & infrastructure tasks
- Pet browsing feature tasks
- Add pet feature tasks
- Edit pet feature tasks
- Delete pet feature tasks
- Integration & E2E testing tasks

**Task Execution**: Developers implement and test tasks in priority order, with each user story independently testable and deployable.

## Implementation Constraints & Dependencies

### Technology Decisions Locked

| Layer | Technology | Version | Rationale |
|-------|-----------|---------|-----------|
| Frontend Build | Vite | 5.0+ | Fast dev server, ES module optimization |
| Frontend UI | React | 18+ | Component model, ecosystem support |
| Frontend Styling | MUI | 5+ | Accessibility, Material Design, theming |
| Frontend Language | JavaScript | ES2020+ | JSDoc types, faster iteration than TS |
| Frontend Testing | Vitest + RTL | Latest | Fast, modern, React-specific utilities |
| Backend Framework | Spring Boot | 3.2+ | Enterprise Java, auto-config, ecosystem |
| Backend ORM | Spring Data JPA | Latest | Reduces boilerplate, standardized queries |
| Backend Testing | JUnit 5 + Mockito | Latest | Modern, annotation-driven, mock framework |
| Database | PostgreSQL | 14+ | Relational integrity, JSONB support, mature |
| DB Migration | Flyway/Liquibase | Latest | Version control for schema changes |
| Code Quality | ESLint + Prettier | Latest | Linting, formatting consistency |

### Critical Assumptions

- ✅ All developers have Node.js 18+ and Java 17+ installed locally
- ✅ PostgreSQL 14+ runs in Docker or is available on local machine
- ✅ GitHub Actions (or equivalent CI) is available for automated testing
- ✅ All team members follow constitution principles (TDD, clean code, PR reviews)
- ✅ Internet connectivity available for npm and Maven package downloads

### Known Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|-----------|
| Image URL broken/invalid | UI displays broken image | Validate URLs in backend; fallback placeholder image in frontend |
| Concurrent pet edits | Data inconsistency | Optimistic locking with version field; collision detection on update |
| Database migration failure | Deployment blocked | Rollback scripts for each migration; staging environment testing |
| API endpoint latency | Poor UX, timeouts | Backend query optimization; caching strategy; loading indicators |
| Component library bugs | Feature blocked | Regular MUI updates; fallback to CSS for critical features |

## Next Steps

### Immediate Actions (Before Phase 0 Research)

1. ✅ **Constitution Check**: PASSED - All 5 principles satisfied
2. ⏳ **Run Phase 0 Research**: Execute `/speckit.plan` → generates `research.md` with dependency findings
3. ⏳ **Phase 1 Design Artifacts**: Generate `data-model.md`, `contracts/`, `quickstart.md`
4. ⏳ **Task Generation**: Run `/speckit.tasks` → creates `tasks.md` with ordered implementation tasks
5. ⏳ **Implementation**: Developers execute tasks following TDD + constitution principles

### Parallel Preparation (During Phase 0-1)

- [ ] Set up GitHub repository with branch protection rules
- [ ] Configure GitHub Actions CI/CD pipeline with quality gates
- [ ] Create `.editorconfig` for cross-IDE consistency
- [ ] Create `.gitignore` for both frontend and backend
- [ ] Initialize Docker `docker-compose.yml` for PostgreSQL
- [ ] Create project READMEs for setup and contribution guidelines
- [ ] Schedule team kickoff meeting to review plan and constitution

## Success Gates

**Phase 1 Design Approval Gate**:
- ✅ Architecture diagram reviewed and approved
- ✅ Data model validated against requirements
- ✅ API contracts reviewed for completeness
- ✅ Quickstart tested for accuracy
- ✅ No ambiguities in design documents

**Phase 2 Task Generation Gate**:
- ✅ Tasks ordered by user story priority
- ✅ Dependencies correctly identified
- ✅ Each task is independently testable
- ✅ Acceptance criteria map to specification requirements

**Implementation Completion Gate**:
- ✅ All user stories implemented and passing acceptance tests
- ✅ Code coverage ≥80% for backend, component tests for frontend
- ✅ Code review completed per constitution requirements
- ✅ E2E tests for critical user journeys passing
- ✅ Documentation updated (API docs, README, schema docs)
- ✅ Constitution compliance verified (clean code, TDD, quality)

---

**Plan Status**: ✅ **COMPLETE - Ready for Phase 0 Research**

**Generated Artifacts**:
- ✅ plan.md (this file) - Architecture, design patterns, workflow, constraints
- ⏳ research.md (Phase 0) - Dependency analysis, technology findings
- ⏳ data-model.md (Phase 1) - Entity models, database schema
- ⏳ contracts/ (Phase 1) - API specifications
- ⏳ quickstart.md (Phase 1) - Development setup guide
- ⏳ tasks.md (Phase 2) - Implementation task list
   - PUT /api/pets/{id} → update pet
   - DELETE /api/pets/{id} → delete pet
   - GET /api/pets/{id} → get single pet

3. **Error Responses** (`contracts/error-responses.md`):
   - Standard error response format
   - Common error codes and messages
   - Validation error structure

4. **Quickstart** (`quickstart.md`):
   - Development environment setup (Node.js, Java, PostgreSQL)
   - Frontend project initialization and commands
   - Backend project initialization and commands
   - Database setup and migration running
   - Local testing and development workflow

### Phase 2: Task Generation & Implementation (After design approval)

**Task Generation**: `/speckit.tasks` creates ordered, dependency-aware implementation tasks organized by user story:
- Setup & infrastructure tasks
- Pet browsing feature tasks
- Add pet feature tasks
- Edit pet feature tasks
- Delete pet feature tasks
- Integration & E2E testing tasks

**Task Execution**: Developers implement and test tasks in priority order, with each user story independently testable and deployable.

## Implementation Constraints & Dependencies

### Technology Decisions Locked

| Layer | Technology | Version | Rationale |
|-------|-----------|---------|-----------|
| Frontend Build | Vite | 5.0+ | Fast dev server, ES module optimization |
| Frontend UI | React | 18+ | Component model, ecosystem support |
| Frontend Styling | MUI | 5+ | Accessibility, Material Design, theming |
| Frontend Language | JavaScript | ES2020+ | JSDoc types, faster iteration than TS |
| Frontend Testing | Vitest + RTL | Latest | Fast, modern, React-specific utilities |
| Backend Framework | Spring Boot | 3.2+ | Enterprise Java, auto-config, ecosystem |
| Backend ORM | Spring Data JPA | Latest | Reduces boilerplate, standardized queries |
| Backend Testing | JUnit 5 + Mockito | Latest | Modern, annotation-driven, mock framework |
| Database | PostgreSQL | 14+ | Relational integrity, JSONB support, mature |
| DB Migration | Flyway/Liquibase | Latest | Version control for schema changes |
| Code Quality | ESLint + Prettier | Latest | Linting, formatting consistency |

### Critical Assumptions

- ✅ All developers have Node.js 18+ and Java 17+ installed locally
- ✅ PostgreSQL 14+ runs in Docker or is available on local machine
- ✅ GitHub Actions (or equivalent CI) is available for automated testing
- ✅ All team members follow constitution principles (TDD, clean code, PR reviews)
- ✅ Internet connectivity available for npm and Maven package downloads

### Known Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|-----------|
| Image URL broken/invalid | UI displays broken image | Validate URLs in backend; fallback placeholder image in frontend |
| Concurrent pet edits | Data inconsistency | Optimistic locking with version field; collision detection on update |
| Database migration failure | Deployment blocked | Rollback scripts for each migration; staging environment testing |
| API endpoint latency | Poor UX, timeouts | Backend query optimization; caching strategy; loading indicators |
| Component library bugs | Feature blocked | Regular MUI updates; fallback to CSS for critical features |
