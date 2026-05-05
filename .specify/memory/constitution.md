<!--
================================================================================
SYNC IMPACT REPORT - Constitution v1.0.0 Ratified 2026-05-05
================================================================================

VERSION CHANGE: N/A → 1.0.0 (Initial Constitution)
BUMP TYPE: INITIAL (New project constitution)
RATIONALE: First iteration of governance framework for Petstore project

MODIFIED PRINCIPLES:
- NEW: I. Feature-First Development (replaces template placeholder)
- NEW: II. Clean Code & Industry Best Practices (replaces template placeholder)
- NEW: III. Test-Driven Development - TDD Mandatory (replaces template placeholder)
- NEW: IV. Responsive & Accessible Design (replaces template placeholder)
- NEW: V. Cross-Stack Quality Assurance (replaces template placeholder)

ADDED SECTIONS:
- Technology Stack Requirements (Frontend, Backend, Database, Dev Environment, Code Quality Tools)
- Development Workflow & Code Review (Branch Strategy, Code Review, Testing Gates, Documentation)

TEMPLATES REQUIRING UPDATES:
- ✅ plan-template.md: "Constitution Check" gate section ALIGNS with Principles I-V
- ✅ spec-template.md: "Success Criteria" section ALIGNS with responsive design and UX requirements
- ✅ tasks-template.md: "Format" and "Organization" sections ALIGN with Feature-First principle

FOLLOW-UP TODOS:
- Setup GitHub branch protection rules enforcing constitution requirements
- Configure CI/CD pipeline with quality gates matching "Testing Gates" section
- Create .editorconfig for cross-IDE consistency
- Setup pre-commit hooks for linting/formatting

AFFECTED ARTIFACTS:
- .specify/memory/constitution.md (CREATED/UPDATED)
- specs/001-petstore-fullstack-app/spec.md (COMPATIBLE - no changes needed)
- .github/copilot-instructions.md (REFERENCE for runtime development guidance)

================================================================================
-->

<!-- Petstore Fullstack Application - Project Constitution -->

# Petstore Constitution

## Core Principles

### I. Feature-First Development (Non-Negotiable)

Every feature MUST be developed with user value as the primary driver. Features begin with clear acceptance criteria and MUST be independently testable and deployable. No feature development without corresponding test coverage. All work maps back to user stories defined in the specification.

**Rationale**: The petstore application serves all ages with a playful experience. Each feature must deliver tangible value (pet browsing, adding, editing, deleting) and be fully functional before moving to the next. This ensures quality and prevents technical debt.

### II. Clean Code & Industry Best Practices (Non-Negotiable)

Code MUST be clean, readable, and maintainable. All code MUST follow language-specific conventions:
- **JavaScript/TypeScript**: Airbnb style guide, meaningful names, avoid nesting >3 levels, document complex logic
- **Java**: Google Java Style Guide, proper package organization, comprehensive Javadoc for public APIs, consistent naming conventions
- **Database**: Normalized schema, clear table/column naming, indexes on foreign keys and frequently queried fields

Comments MUST explain "why", not "what". Complexity MUST be justified and documented. Code reviews MUST verify adherence before merge.

**Rationale**: This is a school project that emphasizes industry standards. Clean code practices prepare contributors for professional development environments and make the codebase maintainable as it evolves.

### III. Test-Driven Development (TDD) Mandatory

All new features MUST follow strict TDD discipline: tests are written and approved by specification → tests fail → implementation → tests pass → refactor. Test coverage for backend services MUST be ≥80%. Frontend component tests MUST cover user interactions and state changes.

Test types required:
- **Unit tests**: Individual functions, components, methods isolated with mocks
- **Integration tests**: API endpoints, database interactions, full user flows
- **E2E tests**: Critical user journeys (pet browsing, CRUD operations)

**Rationale**: TDD ensures correctness, prevents regressions, and documents expected behavior. For a petstore CRUD application, tests verify data integrity and user operations work reliably.

### IV. Responsive & Accessible Design

The UI MUST be responsive across desktop (1920px+), tablet (768px-1024px), and mobile (320px-767px) devices. Material-UI (MUI) MUST be used for consistency. All interactive elements MUST have clear labels, keyboard navigation support, and appropriate ARIA attributes for screen readers. The design MUST be playful and engaging for all ages.

**Rationale**: The petstore is "for everyone" as stated in requirements. Responsive design ensures accessibility; playful design creates engagement and joy.

### V. Cross-Stack Quality Assurance

Quality gates MUST be enforced across all three layers:
- **Frontend**: Component testing, visual regression testing, responsive testing, accessibility audits
- **Backend**: Unit testing, integration testing, API contract testing, database transaction validation
- **Database**: Schema validation, constraint enforcement, migration testing, data integrity checks

No layer is complete until all downstream dependencies pass quality gates.

**Rationale**: The petstore is a full-stack application. Each layer depends on others (frontend on API, API on database). Quality in each layer prevents cascading failures and ensures end-to-end reliability.

## Technology Stack Requirements

**Frontend Stack**:
- Latest Vite as build tool (v5.0+)
- React 18+ for UI library
- Material-UI (MUI) v5+ for component library and design system
- JavaScript with JSDoc annotations for type safety without compilation overhead
- Vitest + React Testing Library for testing
- Axios or Fetch API for HTTP requests
- Environmental variables for API endpoint configuration

**Backend Stack**:
- Java 17 LTS or later
- Spring Boot 3.2+ for REST API framework
- Spring Data JPA for database abstraction
- Maven for dependency management
- JUnit 5 + Mockito for testing
- Lombok for reducing boilerplate
- API documentation with Spring Doc OpenAPI (Swagger)

**Database**:
- PostgreSQL 14+ with JSONB support
- Flyway or Liquibase for schema migrations
- Connection pooling via HikariCP

**Development Environment**:
- Git for version control
- GitHub for repository and collaboration
- Node.js 18+ LTS for frontend tooling
- Maven 3.8+ for backend builds
- Docker for consistency across development environments (optional but recommended)

**Code Quality Tools**:
- ESLint + Prettier for JavaScript (with JSDoc validation)
- Checkstyle + SpotBugs for Java
- SonarQube integration for code metrics
- Pre-commit hooks to enforce formatting and linting
- JSDoc validation for frontend type coverage

## Development Workflow & Code Review

**Branch Strategy**:
- Feature branches named: `[feature-number]-[description]` (e.g., `001-pet-browsing`)
- Main branch MUST always be deployable
- Develop branch for integration testing
- PR-based workflow mandatory for all changes

**Code Review Requirements**:
- All PRs MUST pass automated quality checks (tests, linting, code coverage)
- At least one team member review before merge
- Review comments MUST address logic, style, and adherence to principles
- Reviewer MUST verify test coverage and acceptance criteria satisfaction
- Merge conflicts MUST be resolved by feature branch owner, not reviewer

**Testing Gates**:
- Unit tests MUST pass (≥80% coverage)
- Integration tests MUST pass
- Linting and formatting MUST pass
- E2E tests MUST pass for critical user flows
- No TODO/FIXME comments in production code

**Documentation**:
- API endpoints MUST be documented with Swagger/OpenAPI
- Database schema MUST be documented in README or wiki
- Complex business logic MUST have inline documentation explaining rationale
- README MUST include setup, build, test, and deployment instructions

## Governance

This Constitution supersedes all other project practices and guidelines. It is the source of truth for development standards and quality gates.

**Amendment Process**:
- Amendments require explicit documentation of the rationale and impact
- All team members MUST be notified of changes
- Changed sections MUST be marked with version bump reasoning
- Migration plan MUST be provided if existing work is affected

**Compliance & Enforcement**:
- All PRs are reviewed for constitution compliance
- Violations MUST be resolved before merge
- Recurring violations trigger team discussion and clarification
- Constitution is evaluated and refined during retrospectives

**Version**: 1.0.0 | **Ratified**: 2026-05-05 | **Last Amended**: 2026-05-05
