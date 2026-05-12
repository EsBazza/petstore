# Deployment Checklist: Petstore Application (Render)

## 🏗️ Infrastructure
- [x] **Render.yaml Configuration**: Verified `render.yaml` with correct build/start commands and base path configuration (`/alonzo/api`).
- [x] **Database**: PostgreSQL 14+ managed service configured.
- [x] **Environment Variables**:
    - [x] `PORT=8080`
    - [x] `SPRING_PROFILES_ACTIVE=prod`
    - [x] `VITE_API_BASE_URL` set to the backend's production URL + `/alonzo/api`.

## 🚀 Build & Deployment
- [x] **Backend Build**: Verified `mvn clean package` succeeds with Java 17.
- [x] **Frontend Build**: Verified `npm install && npm run build` succeeds.
- [x] **Flyway Migrations**: Automated on startup.

## ✅ Verification
- [ ] **Health Check**: Endpoint `/alonzo/api/health` returns `200 OK`.
- [ ] **API Connectivity**: Frontend successfully fetches data from `VITE_API_BASE_URL`.
- [ ] **UI/UX**: Sorting and search functionality tested and confirmed.

---
**Status**: Ready for final deployment push.
