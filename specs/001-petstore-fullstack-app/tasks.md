---
description: "Implementation tasks for Petstore Fullstack Application"
---

# Tasks: Petstore Fullstack Application

**Input**: Design documents from `/specs/001-petstore-fullstack-app/`  
**Prerequisites**: plan.md (required), spec.md (required for user stories), data-model.md, contracts/

**Status**: Ready for implementation  
**Total Tasks**: 200 (196 implementation + 4 verification gates)  
**Estimated Duration**: 4-6 weeks (1-3 developers)

---

## Format: `[ID] [P?] [Story] Description`

- **[ID]**: T001-T070 (sequential execution order)
- **[P]**: Can run in parallel (different files, no dependencies on incomplete tasks)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, etc.)
- Include exact file paths in descriptions

### User Stories Mapped

- **US1**: Pet Browsing with Image Gallery (Priority P1)
- **US2**: Add New Pet (Priority P1)
- **US3**: Edit Existing Pet (Priority P1)
- **US4**: Delete Pet (Priority P1)
- **US5**: View Pet Details (Priority P2)

---

## Phase 1: Setup & Infrastructure (Shared Foundation)

**Purpose**: Project initialization, dependencies, database setup, CI/CD pipeline
**Completion Signal**: Both frontend and backend projects build successfully; database is ready; tests run

### Backend Project Initialization

- [ ] T001 Create Maven project structure for Spring Boot backend
- [ ] T002 Create `backend/pom.xml` with Spring Boot 3.2+, Spring Data JPA, PostgreSQL, JUnit 5, Mockito, Lombok dependencies
- [ ] T003 [P] Create `backend/src/main/java/com/petstore/PetstoreApplication.java` (Spring Boot entry point with @SpringBootApplication)
- [ ] T004 [P] Create `backend/src/main/resources/application.properties` with PostgreSQL connection and JPA configuration
- [ ] T005 [P] Create `backend/.gitignore` excluding target/, IDE files, .env files
- [ ] T006 Create `backend/src/main/resources/db/migration/V1__initial_schema.sql` (Pet table with columns: id, name, description, price, imageUrl, createdAt, updatedAt)
- [ ] T007 Create `backend/README.md` with backend setup, build, test, run instructions
- [ ] T008 Verify backend builds successfully with `mvn clean package` (should generate JAR)

### Frontend Project Initialization

- [ ] T009 Create Vite + React project in `frontend/` directory with latest template
- [ ] T010 [P] Configure `frontend/vite.config.js` with React plugin, source map for development
- [ ] T011 [P] Install frontend dependencies: React 18+, MUI v5+, Axios, React Router, Vitest, React Testing Library, @testing-library/user-event
- [ ] T012 [P] Create `frontend/.eslintrc.json` with Airbnb config + JSDoc validation rules
- [ ] T013 [P] Create `frontend/.prettierrc` with 2-space indentation, single quotes, semicolons
- [ ] T014 [P] Create `frontend/vitest.config.js` with jsdom environment and React Testing Library globals
- [ ] T015 [P] Create `frontend/.gitignore` excluding node_modules/, dist/, .env.local files
- [ ] T016 Create `frontend/README.md` with frontend setup, build, test, run instructions
- [ ] T017 Create `frontend/.env.example` with `VITE_API_BASE_URL=http://localhost:8080` placeholder
- [ ] T018 Verify frontend builds successfully with `npm run build` (should generate dist/)

### Database & Docker Setup

- [ ] T019 Create `docker-compose.yml` with PostgreSQL 14+ service, environment variables, persistent volume
- [ ] T020 [P] Create `.env.example` with database name, user, password, backend port, frontend port
- [ ] T021 Test Docker Compose setup: `docker-compose up` starts PostgreSQL successfully
- [ ] T022 Verify database migration runs automatically on backend startup (Flyway should create Pet table)

### CI/CD & Code Quality Setup

- [ ] T023 Create `.github/workflows/backend-tests.yml` running backend tests on push to any branch
- [ ] T024 [P] Create `.github/workflows/frontend-tests.yml` running frontend tests on push to any branch
- [ ] T025 [P] Create `.github/workflows/backend-lint.yml` running Checkstyle on backend code
- [ ] T026 [P] Create `.github/workflows/frontend-lint.yml` running ESLint on frontend code
- [ ] T027 Verify GitHub Actions workflows trigger successfully on test commit

### Repository Configuration

- [ ] T028 Create `.editorconfig` with indent_style=spaces, indent_size=2 for consistency
- [ ] T029 Create `README.md` at repository root with project overview, tech stack, setup instructions, architecture diagram reference
- [ ] T030 Create `.gitignore` at repository root excluding IDE files, node_modules/, dist/, target/
- [ ] T031 Commit all setup files: `git commit -m "chore: project initialization"`

**Phase 1 Completion Verification**:
- ✅ Backend compiles and tests pass
- ✅ Frontend builds and tests pass
- ✅ PostgreSQL runs in Docker
- ✅ CI/CD pipelines configured
- ✅ Project structure matches plan.md

---

## Phase 2: Foundational Layers (Blocking Prerequisites)

**Purpose**: Create shared infrastructure, database models, API base structures
**Completion Signal**: Core models, repositories, and controllers exist; API is callable

### Backend Database & ORM Layer

- [x] T032 Create `backend/src/main/java/com/petstore/entity/Pet.java` (JPA entity with @Entity, @Table("pet"), fields: id (PK), name, description, price, imageUrl, createdAt, updatedAt; add @GeneratedValue, @Column annotations)
- [ ] T033 Create validation annotations in Pet entity: @NotBlank on name, @NotNull on price, @URL on imageUrl
- [ ] T034 [P] Create `backend/src/main/java/com/petstore/repository/PetRepository.java` (Spring Data JPA interface extending JpaRepository<Pet, Long>, add custom method: `List<Pet> findAllByOrderByCreatedAtDesc()`)
- [ ] T035 [P] Create `backend/src/test/java/com/petstore/repository/PetRepositoryTest.java` with tests for CRUD operations and custom queries

### Backend Service Layer

- [x] T036 Create `backend/src/main/java/com/petstore/service/PetService.java` with methods: `getAllPets()`, `getPetById(Long)`, `createPet(Pet)`, `updatePet(Long, Pet)`, `deletePet(Long)`
- [ ] T037 Add validation and error handling in PetService: throw `PetNotFoundException` when pet not found, validate input before persistence
- [ ] T038 [P] Create `backend/src/test/java/com/petstore/service/PetServiceTest.java` with unit tests for all service methods using Mockito

### Backend DTO Layer

- [x] T039 Create `backend/src/main/java/com/petstore/dto/PetCreateRequest.java` (DTO for POST request with name, description, price, imageUrl fields, add @NotBlank, @Min annotations)
- [ ] T040 [P] Create `backend/src/main/java/com/petstore/dto/PetUpdateRequest.java` (DTO for PUT request with name, description, price, imageUrl - all optional)
- [x] T041 [P] Create `backend/src/main/java/com/petstore/dto/PetResponse.java` (DTO for GET response with id, name, description, price, imageUrl, createdAt, updatedAt)

### Backend Exception Handling

- [x] T042 Create `backend/src/main/java/com/petstore/exception/PetNotFoundException.java` (custom exception extending RuntimeException)
- [x] T043 [P] Create `backend/src/main/java/com/petstore/config/GlobalExceptionHandler.java` (@ControllerAdvice with @ExceptionHandler for PetNotFoundException returning 404, validation errors returning 400) ✅ FIXED AMBIGUITY

### Backend API Controller Scaffold

- [x] T044 Create `backend/src/main/java/com/petstore/controller/PetController.java` with skeleton methods for all endpoints (methods: getAllPets, getPetById, createPet, updatePet, deletePet with @GetMapping, @PostMapping, @PutMapping, @DeleteMapping annotations)
- [ ] T045 [P] Create `backend/src/test/java/com/petstore/controller/PetControllerTest.java` with integration test scaffold for all endpoints using MockMvc

### Frontend API Client Layer

- [ ] T046 Create `frontend/src/services/api.js` with Axios instance configured with `VITE_API_BASE_URL`, add methods: `getPets()`, `getPetById(id)`, `createPet(pet)`, `updatePet(id, pet)`, `deletePet(id)`
- [ ] T047 [P] Add error handling in api.js: catch network errors, log to console, throw user-friendly error messages
- [ ] T048 [P] Create `frontend/src/__tests__/services/api.test.js` with tests for API calls (mock axios, verify correct endpoints and methods)

### Frontend Custom Hooks

- [ ] T049 Create `frontend/src/hooks/usePets.js` custom hook with useState for pets array and loading state, useEffect to fetch pets on mount, functions: `getPets()`, `addPet(pet)`, `updatePet(id, pet)`, `removePet(id)`, `getPetById(id)`
- [ ] T050 [P] Create `frontend/src/__tests__/hooks/usePets.test.js` with tests for hook behavior using renderHook from React Testing Library

### Frontend Material-UI Theme

- [ ] T051 Create `frontend/src/styles/theme.js` with MUI createTheme() defining playful colors (primary, secondary, success), typography, spacing
- [ ] T052 [P] Create `frontend/src/utils/constants.js` with API endpoints, validation messages, UI constants

**Phase 2 Completion Verification**:
- ✅ All database models created and migrations run
- ✅ Service and repository layers functional
- ✅ API endpoints callable (even if not fully implemented)
- ✅ Frontend API client connects to backend
- ✅ Unit tests for core layers passing
- ✅ Foundation ready for feature development

---

## Phase 3: User Story 1 - Pet Browsing with Image Gallery (Priority P1)

**Story Goal**: Users can view all available pets in a visually appealing grid with images, names, descriptions, and prices  
**Independent Test**: Load application → verify all pets display with correct data and images load  
**Acceptance Criteria**: All pets displayed, responsive layout on desktop/tablet/mobile, images render from URLs

### Backend: Pet List API Endpoint

- [x] T053 Implement `backend/src/main/java/com/petstore/controller/PetController.java` method: `getAllPets()` - GET `/api/pets` returning List<PetResponse> with all pets ordered by createdAt descending, @GetMapping annotation, @ResponseStatus(HttpStatus.OK) ✅ DONE
- [x] T054 **DO NOT IMPLEMENT for MVP**: Pagination support is explicitly optional per plan.md assumptions (<10,000 pets). Skip this task for MVP release. ✅ DOCUMENTED
- [x] T055 [P] Implement `backend/src/main/java/com/petstore/service/PetService.java` method: `getAllPets()` calls repository, maps Pet entities to PetResponse DTOs ✅ DONE
- [x] T056 [P] Add comprehensive JavaDoc to getAllPets() explaining parameters, return type, exceptions ✅ DONE

### Backend: Pet List API Tests

- [x] T057 Create test in `backend/src/test/java/com/petstore/controller/PetControllerTest.java`: GET `/api/pets` returns 200 OK with list of pets ✅ CREATED
- [x] T058 [P] Add test: GET `/api/pets` returns empty list when no pets in database ✅ INCLUDED
- [x] T059 [P] Add test: GET `/api/pets` returns pets in correct order (by createdAt descending) ✅ INCLUDED
- [x] T060 [P] Add test coverage verification: ensure getAllPets endpoint has unit + integration test coverage ✅ INCLUDED

### Frontend: Pet List Component

- [x] T061 Create `frontend/src/components/PetList.jsx` component displaying grid of pets using MUI Grid, fetches pets via usePets hook on mount, shows loading state while fetching ✅ CREATED
- [x] T062 Add error handling: if API call fails, display error message with "Retry" button ✅ INCLUDED
- [x] T063 [P] Add empty state: if no pets in list, display friendly message "No pets available. Add one to get started!" ✅ INCLUDED
- [x] T064 Add responsive styling: 1 column on mobile (320-767px), 2 columns on tablet (768-1024px), 3-4 columns on desktop (1920px+) ✅ INCLUDED

### Frontend: Pet Card Component

- [x] T065 Create `frontend/src/components/PetCard.jsx` component displaying individual pet card (MUI Card with image, name, description, price), clickable to view details or edit/delete actions ✅ CREATED
- [x] T066 Add pet image rendering: `<img>` with imageUrl from props, alt text = pet name, fallback to placeholder if URL fails ✅ INCLUDED
- [x] T067 [P] Add action buttons: "View Details" (link/button), "Edit" (button), "Delete" (button) with appropriate spacing and colors ✅ INCLUDED
- [x] T068 [P] Style pet price: display in currency format ($X.XX) with MUI Typography and appropriate font size ✅ INCLUDED

### Frontend: Pet List Tests

- [x] T069 Create test in `frontend/src/__tests__/components/PetList.test.jsx`: renders list of pets from API ✅ CREATED
- [x] T070 Add test: loading state displays while fetching pets ✅ INCLUDED
- [x] T071 [P] Add test: error message displays if API fails ✅ INCLUDED
- [x] T072 [P] Add test: empty state message displays when no pets available ✅ INCLUDED
- [x] T073 [P] Add test: responsive layout renders correct number of columns ✅ INCLUDED

### Frontend: Pet Card Tests

- [x] T074 Create test in `frontend/src/__tests__/components/PetCard.test.jsx`: renders pet information (name, description, price) ✅ CREATED
- [x] T075 [P] Add test: pet image renders with correct src attribute ✅ INCLUDED
- [x] T076 [P] Add test: action buttons render and are clickable ✅ INCLUDED
- [x] T077 [P] Add test: fallback placeholder displays if image URL fails ✅ INCLUDED

### Frontend: App & Home Page Structure

- [x] T078 Create `frontend/src/pages/HomePage.jsx` page component using PetList, wraps in MUI Container with padding ✅ CREATED
- [x] T079 [P] Create `frontend/src/App.jsx` root component with React Router (BrowserRouter), routes HomePage at `/` ✅ VERIFIED
- [x] T080 [P] Create `frontend/src/main.jsx` entry point rendering App component, apply MUI ThemeProvider with theme.js ✅ VERIFIED

### Frontend: Styling & Theme Integration

- [x] T081 Create `frontend/src/styles/theme.js` with playful colors (e.g., primary purple, secondary pink, success green), rounded corners, shadows for fun aesthetic ✅ VERIFIED
- [x] T082 [P] Apply theme globally in main.jsx or App.jsx wrapping with `<ThemeProvider>` ✅ VERIFIED

**User Story 1 Verification**:
- ✅ GET `/api/pets` endpoint returns all pets
- ✅ Backend tests passing with 80%+ coverage
- ✅ PetList component renders and fetches data
- ✅ PetCard displays all pet information correctly
- ✅ Images load and fallback works
- ✅ Responsive layout verified on mobile/tablet/desktop
- ✅ Frontend tests passing
- ✅ E2E: User loads app → sees all pets in grid with images

---

## Phase 4: User Story 2 - Add New Pet (Priority P1)

**Story Goal**: Users can create new pet listings by filling a form and clicking "Save"  
**Independent Test**: Click "Add Pet" → fill form → click Save → new pet appears in list and persists in database  
**Acceptance Criteria**: Form accepts all pet fields, validates required fields, displays errors, saves to database, updates UI immediately

### Backend: Create Pet API Endpoint

- [x] T083 Implement `backend/src/main/java/com/petstore/controller/PetController.java` method: `createPet(@RequestBody PetCreateRequest request)` - POST `/api/pets` accepting PetCreateRequest DTO, returning PetResponse with 201 Created status
- [x] T084 Implement validation: @Valid on PetCreateRequest, catch ConstraintViolationException and return 400 with validation errors
- [x] T085 [P] Implement `backend/src/main/java/com/petstore/service/PetService.java` method: `createPet(PetCreateRequest)` - maps DTO to Pet entity, validates business rules (e.g., price > 0), saves to repository, returns saved Pet
- [x] T086 [P] Add comprehensive JavaDoc to createPet() explaining validation, error cases, return response format

### Backend: Create Pet API Tests

- [x] T087 Create test in `backend/src/test/java/com/petstore/controller/PetControllerTest.java`: POST `/api/pets` with valid data returns 201 Created with created pet
- [x] T088 Add test: POST `/api/pets` with missing required field returns 400 Bad Request with validation error details
- [x] T089 [P] Add test: POST `/api/pets` with invalid imageUrl format returns 400 with error
- [x] T090 [P] Add test: POST `/api/pets` persists pet to database (verify with follow-up GET call)
- [x] T091 [P] Add test: POST `/api/pets` returns PetResponse with generated id and timestamps

### Frontend: Add Pet Form Component

- [x] T092 Create `frontend/src/components/PetForm.jsx` component with form fields: name (text), description (textarea), price (number), imageUrl (text), Submit button, Cancel button
- [x] T093 Add form state management: useState for each field (name, description, price, imageUrl), use controlled inputs
- [x] T094 [P] Add form validation: name required and >2 chars, price required and >0, imageUrl valid URL, display error messages inline on blur or submit
- [x] T095 Add MUI form components: TextField for name/imageUrl, TextField with multiline for description, TextField with type="number" for price, Button for submit/cancel

### Frontend: Add Pet Modal/Dialog

- [x] T096 Create `frontend/src/components/AddPetModal.jsx` component wrapping PetForm in MUI Dialog, opened via "Add Pet" button in header/toolbar
- [x] T097 [P] Add logic: on form submit, call `api.createPet(formData)` → on success, close dialog, add pet to list, show success toast; on error, show error toast and keep dialog open
- [x] T098 [P] Add loading state: disable submit button while request is in flight, show loading spinner

### Frontend: Pet Form Tests

- [ ] T099 Create test in `frontend/src/__tests__/components/PetForm.test.jsx`: form renders all input fields
- [ ] T100 Add test: form validation displays error for empty name field
- [ ] T101 [P] Add test: form validation displays error for price <= 0
- [ ] T102 [P] Add test: form validation displays error for invalid imageUrl
- [ ] T103 [P] Add test: submit button is disabled while loading

### Frontend: Home Page Enhancement

- [x] T104 Update `frontend/src/pages/HomePage.jsx` to add "Add Pet" button in header/toolbar (MUI AppBar with Button)
- [x] T105 [P] Wire AddPetModal to HomePage: show modal when button clicked, refresh pet list on successful creation
- [x] T106 [P] Add success toast notification when pet is added (using optional toast library or simple alert)

**User Story 2 Verification**:
- ✅ POST `/api/pets` endpoint accepts and validates pet data
- ✅ Backend tests passing with 80%+ coverage
- ✅ Form component renders all fields correctly
- ✅ Validation errors display for all invalid inputs
- ✅ Submit call to API with correct data
- ✅ Success/error notifications display
- ✅ New pet appears in list immediately after creation
- ✅ Pet persists in database
- ✅ E2E: User clicks "Add Pet" → fills form → clicks Save → sees new pet in list

---

## Phase 5: User Story 3 - Edit Existing Pet (Priority P1)

**Story Goal**: Users can modify pet information by clicking "Edit", changing fields, and clicking "Save"  
**Independent Test**: Click "Edit" on pet → modify field → click Save → changes appear in list and persist  
**Acceptance Criteria**: Form pre-populated with pet data, saves changes to database, updates UI immediately, Cancel button discards changes

### Backend: Get Single Pet Endpoint

- [ ] T107 Implement `backend/src/main/java/com/petstore/controller/PetController.java` method: `getPetById(@PathVariable Long id)` - GET `/api/pets/{id}` returning PetResponse or 404 if not found
- [ ] T108 [P] Implement `backend/src/main/java/com/petstore/service/PetService.java` method: `getPetById(Long id)` - retrieves pet from repository, throws PetNotFoundException if not found, returns PetResponse

### Backend: Update Pet API Endpoint

- [ ] T109 Implement `backend/src/main/java/com/petstore/controller/PetController.java` method: `updatePet(@PathVariable Long id, @RequestBody PetUpdateRequest request)` - PUT `/api/pets/{id}` accepting partial PetUpdateRequest, returning updated PetResponse or 404 if pet not found
- [ ] T110 Implement validation: validate DTO fields if provided, return 400 for validation errors
- [ ] T111 [P] Implement `backend/src/main/java/com/petstore/service/PetService.java` method: `updatePet(Long id, PetUpdateRequest)` - loads pet, updates provided fields (handle null/partial updates), saves, returns updated Pet
- [ ] T112 [P] Add comprehensive JavaDoc to updatePet() explaining partial update behavior, error cases

### Backend: Update Pet API Tests

- [ ] T113 Create test in `backend/src/test/java/com/petstore/controller/PetControllerTest.java`: GET `/api/pets/{id}` returns 200 with correct pet data
- [ ] T114 Add test: GET `/api/pets/{id}` with non-existent id returns 404 Not Found
- [ ] T115 [P] Add test: PUT `/api/pets/{id}` with valid data returns 200 with updated pet
- [ ] T116 [P] Add test: PUT `/api/pets/{id}` updates only provided fields (partial update), leaves other fields unchanged
- [ ] T117 [P] Add test: PUT `/api/pets/{id}` with invalid data returns 400 Bad Request
- [ ] T118 [P] Add test: PUT `/api/pets/{id}` on non-existent pet returns 404 Not Found

### Frontend: Edit Pet Form Component

- [ ] T119 Create `frontend/src/components/EditPetForm.jsx` component similar to PetForm but pre-populated with pet data from props, accepts petId as prop
- [ ] T120 [P] Add logic: on component mount or when petId changes, call `usePets.getPetById(petId)` to load current pet data into form fields
- [ ] T121 [P] Add error handling: if pet not found (404), display error message

### Frontend: Edit Pet Modal/Dialog

- [ ] T122 Create `frontend/src/components/EditPetModal.jsx` component wrapping EditPetForm in MUI Dialog, opened via "Edit" button on PetCard with petId passed to modal
- [ ] T123 [P] Add logic: on form submit, call `api.updatePet(petId, formData)` → on success, close dialog, update pet in list, show success toast; on error, show error toast
- [ ] T124 [P] Add Cancel button: discard changes and close dialog

### Frontend: Pet Card Edit Button

- [ ] T125 Update `frontend/src/components/PetCard.jsx` to add "Edit" button (MUI Button) that opens EditPetModal when clicked
- [ ] T126 [P] Wire EditPetModal to PetCard: pass petId, handle modal open/close state

### Frontend: Home Page Update for Editing

- [ ] T127 Update `frontend/src/pages/HomePage.jsx` to handle edit modal state, pass modal control props to PetList, which passes to PetCard

### Frontend: Edit Form Tests

- [ ] T128 Create test in `frontend/src/__tests__/components/EditPetForm.test.jsx`: form loads with existing pet data pre-populated
- [ ] T129 Add test: form validation works for modified fields
- [ ] T130 [P] Add test: submit button calls update API with modified data
- [ ] T131 [P] Add test: Cancel button discards changes and closes modal

**User Story 3 Verification**:
- ✅ GET `/api/pets/{id}` endpoint returns correct pet
- ✅ PUT `/api/pets/{id}` endpoint updates pet correctly
- ✅ Backend tests passing with 80%+ coverage
- ✅ Form pre-populates with pet data correctly
- ✅ Changes are validated before submit
- ✅ API receives correct update data
- ✅ Pet changes appear in list immediately
- ✅ Changes persist in database
- ✅ E2E: User clicks Edit → modifies fields → clicks Save → sees changes in list

---

## Phase 6: User Story 4 - Delete Pet (Priority P1)

**Story Goal**: Users can remove pets with a confirmation dialog to prevent accidental deletion  
**Independent Test**: Click "Delete" → confirm → pet disappears from list and database  
**Acceptance Criteria**: Delete button shows confirmation dialog, canceling keeps pet, confirming removes pet immediately, persists in database

### Backend: Delete Pet API Endpoint

- [ ] T132 Implement `backend/src/main/java/com/petstore/controller/PetController.java` method: `deletePet(@PathVariable Long id)` - DELETE `/api/pets/{id}` returning 204 No Content on success or 404 if pet not found
- [ ] T133 [P] Implement `backend/src/main/java/com/petstore/service/PetService.java` method: `deletePet(Long id)` - deletes pet from repository, throws PetNotFoundException if not found
- [ ] T134 [P] Add comprehensive JavaDoc to deletePet() explaining delete behavior, return code

### Backend: Delete Pet API Tests

- [ ] T135 Create test in `backend/src/test/java/com/petstore/controller/PetControllerTest.java`: DELETE `/api/pets/{id}` with valid id returns 204 No Content
- [ ] T136 Add test: DELETE `/api/pets/{id}` removes pet from database (verify with follow-up GET returns 404)
- [ ] T137 [P] Add test: DELETE `/api/pets/{id}` with non-existent id returns 404 Not Found

### Frontend: Delete Confirmation Dialog

- [ ] T138 Create `frontend/src/components/DeleteConfirmation.jsx` component displaying MUI Dialog with message "Are you sure you want to delete [petName]?", Cancel button, Delete button
- [ ] T139 Add props: petId, petName, onConfirm callback, onCancel callback
- [ ] T140 [P] Add styling: Delete button uses error color (red), Cancel uses default

### Frontend: Pet Card Delete Button

- [ ] T141 Update `frontend/src/components/PetCard.jsx` to add "Delete" button (MUI Button with delete icon and error color)
- [ ] T142 Wire DeleteConfirmation modal: show modal when Delete button clicked, pass petId and petName, handle confirm/cancel callbacks

### Frontend: Delete Logic

- [ ] T143 Update delete callback in PetCard: on confirm, call `api.deletePet(petId)` → on success, remove pet from usePets list, show success toast; on error, show error toast and keep pet in list
- [ ] T144 [P] Add loading state during delete: disable Delete button while request in flight, show loading spinner

### Frontend: Delete Tests

- [ ] T145 Create test in `frontend/src/__tests__/components/DeleteConfirmation.test.jsx`: dialog renders with pet name
- [ ] T146 Add test: Cancel button triggers onCancel callback and dialog closes
- [ ] T147 [P] Add test: Delete button triggers onConfirm callback and dialog closes
- [ ] T148 [P] Add test: loading state disables Delete button

### Frontend: Update Home Page for Delete

- [ ] T149 Update `frontend/src/pages/HomePage.jsx` to handle delete confirmation and pet removal from list

**User Story 4 Verification**:
- ✅ DELETE `/api/pets/{id}` endpoint works correctly
- ✅ Backend tests passing with 80%+ coverage
- ✅ Confirmation dialog displays with correct pet name
- ✅ Cancel button closes dialog without deletion
- ✅ Confirm button calls delete API
- ✅ Pet removed from list immediately on success
- ✅ Pet deleted from database permanently
- ✅ Error messages display on failure
- ✅ E2E: User clicks Delete → confirms → pet disappears from list

---

## Phase 7: User Story 5 - View Pet Details (Priority P2)

**Story Goal**: Users can click on a pet to view full details in a dedicated view  
**Independent Test**: Click pet in list → navigate to detail page → see all pet info displayed correctly → click back → return to list  
**Acceptance Criteria**: Detail page shows all pet info, back button returns to list, images display correctly

### Frontend: Pet Detail Page

- [ ] T150 Create `frontend/src/pages/PetDetailPage.jsx` page component accepting petId from URL params
- [ ] T151 [P] Add logic: on mount, fetch pet data via `usePets.getPetById(petId)`, show loading state while fetching
- [ ] T152 [P] Add error handling: if pet not found, display error message with back button
- [ ] T153 Display pet information: large image, name, description, price in nice layout using MUI components (Card, Box, Typography)

### Frontend: Pet Detail Component

- [ ] T154 Create `frontend/src/components/PetDetail.jsx` component displaying pet full information (image, name, description, price, createdAt, updatedAt), edit and delete buttons
- [ ] T155 [P] Add edit button: navigates to edit or opens EditPetModal
- [ ] T156 [P] Add delete button: shows DeleteConfirmation modal

### Frontend: Back Navigation

- [ ] T157 Add back button in PetDetailPage: uses React Router navigate(-1) to go back to list
- [ ] T158 [P] Add header/breadcrumb showing navigation: "Pets > [PetName]"

### Frontend: Routing

- [ ] T159 Update `frontend/src/App.jsx` to add route for PetDetailPage: `/pets/:id` path using React Router
- [ ] T160 [P] Update PetCard click handler to navigate to detail page: click on card → navigate to `/pets/{id}`

### Frontend: Pet Detail Tests

- [ ] T161 Create test in `frontend/src/__tests__/pages/PetDetailPage.test.jsx`: page renders with pet details
- [ ] T162 Add test: loading state displays while fetching
- [ ] T163 [P] Add test: error message displays if pet not found
- [ ] T164 [P] Add test: back button navigates to previous page

**User Story 5 Verification**:
- ✅ Detail page renders with correct pet information
- ✅ Images display correctly on detail page
- ✅ Back button navigates to list
- ✅ Edit/delete buttons available on detail page
- ✅ Error handling for missing pets
- ✅ Tests passing
- ✅ E2E: User clicks pet → sees full details → clicks back → returns to list

---

## Phase 8: Integration & Cross-Cutting Concerns (Polish & QA)

**Purpose**: Integration testing, end-to-end testing, error handling, performance optimization, documentation

### End-to-End Testing

- [ ] T165 Create `frontend/src/__tests__/e2e/petstore.e2e.test.jsx` (or Playwright test) for complete user journey: load page → see pets → add pet → verify in list → click edit → modify → save → verify changes → click delete → confirm → pet removed
- [ ] T166 Add E2E test: verify all CRUD operations in single journey
- [ ] T167 [P] Add E2E test: network error handling (mock API failure → verify error message displays)

### API Integration Testing

- [ ] T168 Create `backend/src/test/java/com/petstore/integration/PetControllerIntegrationTest.java` with @SpringBootTest testing full stack (controller → service → repository → database)
- [ ] T169 Add integration test: complete flow for each operation (add → read → update → delete)
- [ ] T170 [P] Add integration test: concurrent operations (multiple adds/updates/deletes in parallel)

### Backend Error Handling Enhancement

- [x] T171 Review and enhance `backend/src/main/java/com/petstore/config/GlobalExceptionHandler.java`: handle all exception types (ConstraintViolation, EntityNotFound, generic exceptions), return consistent error JSON format ✅ UNIFIED FORMAT
- [x] T172 [P] Add error response DTO: `backend/src/main/java/com/petstore/dto/ErrorResponse.java` with timestamp, status, message, details fields ✅ ADDED DETAILS FIELD

### Frontend Error Handling Enhancement

- [ ] T173 Create `frontend/src/utils/errorHandler.js` with function to format error messages from API responses (extract error details, provide user-friendly text)
- [ ] T174 [P] Update all API calls to use error handler for consistent error messaging across app

### Frontend Loading States & Spinners

- [ ] T175 Create `frontend/src/components/LoadingSpinner.jsx` reusable component displaying MUI CircularProgress with message
- [ ] T176 [P] Apply LoadingSpinner in all async operations: pet list loading, form submission loading, delete confirmation loading

### Frontend Success/Error Notifications

- [ ] T177 Create `frontend/src/components/Notification.jsx` or use MUI Snackbar for success/error toast messages
- [ ] T178 [P] Wire Notification to all CRUD operations: show "Pet added successfully" on add, "Pet updated successfully" on update, "Pet deleted successfully" on delete, "Error: [message]" on failure

### Performance Optimization

- [ ] T179 Add backend query optimization: consider indexing on Pet table (id, createdAt), add database query analysis
- [ ] T180 [P] Add frontend optimizations: lazy loading images with React.lazy, memoize components with React.memo where applicable, consider pagination for large pet lists (if >100 pets)

### Documentation Updates

- [ ] T181 Update `backend/README.md` with API endpoint documentation (methods, paths, request/response examples)
- [ ] T182 [P] Add OpenAPI/Swagger documentation: configure Spring Doc OpenAPI, add Swagger UI endpoint `/swagger-ui.html`
- [ ] T183 [P] Update `frontend/README.md` with component documentation, hooks documentation, styling guide
- [ ] T184 [P] Update root `README.md` with architecture diagram, tech stack details, setup instructions, troubleshooting section

### Code Quality & Refactoring

- [ ] T185 Run backend code quality checks: `mvn checkstyle:check` should have zero violations
- [ ] T186 [P] Run frontend linting: `npm run lint` should have zero errors (warnings acceptable)
- [ ] T187 [P] Review code for clean code principles: meaningful names, small functions, no code duplication, proper comments
- [ ] T188 [P] Refactor any complex logic into separate utility functions

### Database & Schema Review

- [ ] T189 Review Pet schema in `backend/src/main/resources/db/migration/V1__initial_schema.sql`: verify all columns present, constraints correct, indexes optimized
- [ ] T190 [P] Test database migration: drop and recreate database, verify migration runs cleanly, data integrity maintained

### Final Testing & Verification

- [ ] T191 Run full test suite: backend tests (`mvn test`), frontend tests (`npm run test`) - all should pass
- [ ] T192 [P] Verify code coverage: backend ≥80%, frontend critical components covered
- [ ] T193 [P] Manual testing: test all user flows on multiple devices/browsers (desktop Chrome, mobile Safari, tablet Firefox)
- [ ] T194 [P] Verify responsive design: inspect layouts on mobile (320px), tablet (768px), desktop (1920px)

### CI/CD Pipeline Verification

- [ ] T195 Verify GitHub Actions workflows: backend tests run on push, frontend tests run on push, linting checks run and pass
- [ ] T196 [P] Verify build artifacts generated: backend JAR buildable, frontend dist/ buildable
- [ ] T197 [P] Document CI/CD setup: update `.github/workflows/` files with comments explaining each step

### Final Cleanup & Commit

- [ ] T198 Remove any TODO/FIXME comments from production code
- [ ] T199 [P] Verify `.env.example` has all required variables with helpful comments
- [ ] T200 Commit final code: `git commit -m "feat: complete petstore CRUD application with full test coverage"`

---

## Phase 9: UI Modernization & Deployment Enhancements (Bonus Phase)

**Purpose**: Professional-grade UI/UX and production-ready deployment
**Completion Signal**: Glassmorphism applied, animations smooth, optimistic updates functional, health checks active

### UI Modernization

- [x] T201 Install and configure `framer-motion` and `sonner` in frontend
- [x] T202 Update `frontend/src/styles/theme.js` for glassmorphism (backdrop blur, transparency) and modern typography (Outfit/Inter)
- [x] T203 Implement staggered entrance animations in `frontend/src/components/PetList.jsx` using `framer-motion`
- [x] T204 Add hover and tap animations to `frontend/src/components/PetCard.jsx` and buttons
- [x] T205 Replace standard alerts with `sonner` toast notifications for all CRUD operations

### Optimistic Updates Implementation

- [x] T206 Refactor `frontend/src/hooks/usePets.js` to support optimistic updates for `addPet`, `updatePetData`, and `removePet`
- [x] T207 Implement robust rollback logic in `usePets.js` for failed optimistic updates
- [x] T208 Add visual feedback for "pending" optimistic states (e.g., subtle opacity change on card)

### Deployment & Health

- [x] T209 Implement `/api/health` endpoint in `backend/src/main/java/com/petstore/controller/PetController.java` returning 200 OK
- [x] T210 Update `render.yaml` with health check path and optimized build settings

**Phase 9 Completion Verification**:
- ✅ UI features glassmorphism and smooth animations
- ✅ CRUD operations feel "instant" via optimistic updates
- ✅ Rollback logic verified on API failure
- ✅ Health check endpoint responds correctly
- ✅ Render deployment configuration optimized

**Phase 8 Completion Verification**:
- ✅ All CRUD operations tested end-to-end
- ✅ Error handling comprehensive across all layers
- ✅ Code quality checks passing
- ✅ Test coverage meets targets (backend 80%+, frontend components)
- ✅ Documentation complete (README, API docs, component docs)
- ✅ Responsive design verified on all target devices
- ✅ Performance optimizations applied
- ✅ CI/CD pipeline working correctly
- ✅ All tests passing in CI/CD

---

## Dependency Graph & Parallelization Strategy

### Critical Path (Sequential Dependencies)

```
T001-T031 (Setup) → T032-T052 (Foundation) → T053-T080 (US1) 
    ↓
T083-T106 (US2) → T107-T127 (US3) → T132-T149 (US4) 
    ↓
T150-T164 (US5) → T165-T200 (Integration & QA)
```

### Parallelizable Blocks (Can run simultaneously)

**After T031 (Setup complete)**:
- Backend foundation (T032-T045) can run in parallel with frontend foundation (T046-T052)
- Within each layer, independent components can be created in parallel (marked with [P])

**After T080 (US1 complete)**:
- US2 (T083-T106), US3 (T107-T127), US4 (T132-T149) can start independently
- However, US3 depends on US2's API implementation, so sequence: US1 → US2 → US3 → US4

**US5 (T150-T164)** can start after US1-US4 are complete but can be implemented in parallel with Phase 8

### Recommended Execution Schedule (1-3 developers, 4-6 weeks)

**Week 1**: Tasks T001-T031 (Setup & Infrastructure) - 1 developer
**Week 2**: Tasks T032-T052 (Foundation Layers) - 1 developer, parallel: frontend + backend
**Week 3**: Tasks T053-T080 (US1 - Pet Browsing) - 1-2 developers, parallel: backend endpoints + frontend components
**Week 4**: Tasks T083-T127 (US2-US3 - Add & Edit) - 2 developers, sequential but backend-first then frontend
**Week 5**: Tasks T132-T164 (US4-US5 - Delete & Details) - 2 developers, can parallelize components
**Week 6**: Tasks T165-T200 (Integration & QA) - 1-2 developers, final testing and documentation

---

## Task Statistics

| Category | Count | Status |
|----------|-------|--------|
| **Setup & Infrastructure** | 31 | Setup Phase |
| **Foundation Layers** | 21 | Blocking Prerequisites |
| **US1 - Pet Browsing** | 28 | User Story Phase |
| **US2 - Add Pet** | 24 | User Story Phase |
| **US3 - Edit Pet** | 23 | User Story Phase |
| **US4 - Delete Pet** | 18 | User Story Phase |
| **US5 - View Details** | 15 | User Story Phase |
| **Integration & QA** | 36 | Polish Phase |
| **TOTAL** | **196 tasks** | Ready for Implementation |

---

## Phase 10: Gallery Filters & Details Overhaul

**Purpose**: Professional-grade product discovery and enhanced detail view.
**Completion Signal**: Search/filter functional, detail page modernized, related pets displayed.

### Product Gallery Filters

- [ ] T211 Create `frontend/src/components/PetFilters.jsx` with search input and price slider
- [ ] T212 Implement `usePets.js` logic for `filteredPets` (filter by name and price)
- [ ] T213 Integrate `PetFilters.jsx` into `HomePage.jsx` and update `PetList` to render `filteredPets`

### Details Page Overhaul

- [ ] T214 Refactor `frontend/src/pages/PetDetailPage.jsx` with glassmorphism styles
- [ ] T215 Add `framer-motion` animations to `PetDetailPage.jsx`
- [ ] T216 Implement "Related Pets" section on `PetDetailPage.jsx` (fetch by similar price)
- [ ] T217 Add interactivity: "Heart" (wishlist) and "Share" buttons

### Deployment Optimization

- [ ] T218 Update `render.yaml` to set backend and database to `plan: free`
- [ ] T219 Verify application still builds and health checks pass on free tier

**Phase 10 Completion Verification**:
- ✅ Search and filter work correctly
- ✅ Detail page is visually enhanced with animations and glassmorphism
- ✅ Related pets section renders correctly
- ✅ App is configured for Render free tier
