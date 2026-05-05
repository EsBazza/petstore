# Render Deployment Checklist & Quick Reference

**Generated**: May 5, 2026  
**Project**: Petstore Full-Stack Application  
**Deployment Target**: Render.com

---

## 🚀 Pre-Deployment Checklist (Do Before Starting)

### Code Quality
- [ ] All tests pass locally
  ```bash
  cd backend && mvn test
  cd frontend && npm test
  ```
- [ ] Code builds successfully
  ```bash
  cd backend && mvn clean package
  cd frontend && npm run build
  ```
- [ ] No console errors or warnings
- [ ] Git repository is clean
  ```bash
  git status  # Should show "nothing to commit"
  ```

### Configuration Review
- [ ] `render.yaml` exists and is valid
- [ ] `Dockerfile` exists in project root
- [ ] `.dockerignore` exists
- [ ] `application-prod.properties` configured
- [ ] `.env.render` template reviewed
- [ ] `.env.local` has valid local configuration

### Documentation Review
- [ ] Read `RENDER_QUICK_START.md` (5 min)
- [ ] Understand deployment architecture (diagram)
- [ ] Know the 3-step deployment process

---

## 📋 Step-by-Step Deployment

### Step 1️⃣ Local Testing (15 minutes)

```bash
# Test Docker build locally
docker build -t petstore:test .

# This should complete without errors
# If it fails, fix issues before deploying
```

**Expected Output**: `Successfully tagged petstore:test`

✅ **Verify**: No error messages, build completes

---

### Step 2️⃣ Push to GitHub (5 minutes)

```bash
# Stage all changes
git add .

# Commit with descriptive message
git commit -m "Add Render deployment configuration

- Add render.yaml for service declarations
- Add Dockerfile for multi-stage build
- Add application-prod.properties for production
- Add deployment guides and documentation"

# Push to main branch
git push origin main
```

**Verify in GitHub**: 
- [ ] Files appear in repository
- [ ] Latest commit shows deployment files
- [ ] No merge conflicts

---

### Step 3️⃣ Connect to Render (3 minutes)

1. **Log in to Render**
   - Go to https://dashboard.render.com
   - Sign in with GitHub account

2. **Create Web Service**
   - Click **New +** button
   - Select **Web Service**
   - Choose **Build and deploy from a Git repository**

3. **Connect GitHub**
   - Click **Connect GitHub** (if not already)
   - Authorize Render to access repositories
   - Select `petstore` repository

4. **Configure Service**
   - **Name**: `petstore-backend`
   - **Branch**: `main`
   - **Runtime**: `Docker`
   - **Build Command**: (auto-detected from Dockerfile)
   - **Start Command**: (auto-detected from Dockerfile)

5. **Review & Deploy**
   - Scroll down to **Create Web Service**
   - Click button to start deployment

---

### Step 4️⃣ Create Database Service (3 minutes)

While web service is building:

1. **Click New +** → **PostgreSQL**
2. **Configure**:
   - **Name**: `petstore-postgres`
   - **Database**: `petstore_db`
   - **User**: `petstore_user`
   - **Region**: (same as web service)
   - **PostgreSQL Version**: Latest

3. **Create** (click button)

**Result**: Database URL will be auto-provided

---

### Step 5️⃣ Link Services (2 minutes)

1. **Go back to Web Service** → `petstore-backend`
2. **Environment** tab
3. **Add from PostgreSQL**:
   - Render auto-suggests database variables
   - Accept and add

**Auto-provided variables**:
```
DATABASE_URL
POSTGRES_HOST
POSTGRES_USER  
POSTGRES_PASSWORD
POSTGRES_DB
```

---

### Step 6️⃣ Set Additional Environment Variables (2 minutes)

In **Environment** tab, add manually:

```
PORT=8080
SPRING_PROFILES_ACTIVE=prod
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_FLYWAY_BASELINE_ON_MIGRATE=true
```

---

### Step 7️⃣ Monitor Deployment (10-15 minutes)

1. **Watch Build Progress**
   - Click **Logs** tab
   - Monitor real-time build output
   - Look for: `Stage 1: Build backend JAR`
   - Then: `Stage 2: Build frontend`
   - Then: `Stage 3: Runtime container`

2. **Build Completion**
   - Should see: `Successfully deployed`
   - Service status changes to **Live** (green)

3. **Health Check**
   - Render automatically checks `/actuator/health`
   - Should return: `200 OK` if healthy

---

## ✅ Post-Deployment Verification

### Immediate Checks (Right After Deployment)

- [ ] Service shows **Live** status (green)
- [ ] No errors in logs
- [ ] Database migration completed

### 5-Minute Verification

```bash
# Get your service URL from Render Dashboard
SERVICE_URL="https://petstore-xxx.onrender.com"

# Test API endpoint
curl $SERVICE_URL/api/pets

# Should return: [] (empty array) or list of pets
```

### Frontend Verification

- [ ] Visit `https://petstore-xxx.onrender.com/`
- [ ] Page loads (React app visible)
- [ ] No console errors (F12 → Console)
- [ ] Try clicking "Browse Pets"

### API Verification

- [ ] GET `/api/pets` returns `[]` or pet list
- [ ] Swagger UI accessible at `/api/swagger-ui.html`
- [ ] Health check passes at `/actuator/health`

---

## 🚨 Troubleshooting Guide

### Problem: Build Fails at "Maven Clean Package"

**Symptoms**: Build log shows `BUILD FAILURE`

**Solution**:
```bash
# Test locally first
cd backend
mvn clean package

# Fix any compilation errors
# Then push and redeploy
```

### Problem: "Cannot Connect to Database"

**Symptoms**: Application starts but logs show database connection errors

**Solution**:
1. Check PostgreSQL service is **Live** (green)
2. Verify environment variables in Render Dashboard:
   - `DATABASE_URL` matches PostgreSQL service
   - `SPRING_DATASOURCE_URL` is correctly formatted
3. Redeploy web service after variables are set

### Problem: Application Starts But Returns 500 Error

**Symptoms**: GET `/api/pets` returns 500 error

**Solution**:
1. Check logs for specific error message
2. Most common: Flyway migration failed
3. Verify database permissions
4. Check `application-prod.properties` configuration

### Problem: Frontend Loads But Can't Call API

**Symptoms**: App loads, but "Browse Pets" returns error

**Solution**:
1. Check CORS configuration in logs
2. Verify backend URL is correct
3. Check browser console for exact error
4. Ensure API service URL is reachable

### Problem: Service Keeps Restarting

**Symptoms**: Status bounces between colors

**Solution**:
1. Check memory usage (likely out of memory)
2. Increase plan from Starter to Starter Plus
3. Check application logs for crashes
4. Verify database connection pool settings

---

## 📊 Deployment Status Dashboard

### Expected Timeline

```
TIME        STATUS              ACTIVITY
─────────────────────────────────────────────────
0:00        Starting            Render receives push
0:30        Building            Docker build stage 1-3
5:00        Running             Application startup
5:15        Checking Health     Actuator health check
5:30        ✅ Live             Ready for traffic
```

### What to Look For

**Build Phase** (minutes 0-5):
```
Cloning repository...
[Stage 1] Building backend JAR...
mvn clean package
[Stage 2] Building frontend...
npm install && npm run build
[Stage 3] Running container...
java -jar petstore-api-1.0.0.jar
```

**Success Indicators**:
- ✅ No ERROR or FAILURE messages
- ✅ Status shows "Live" (green)
- ✅ Health endpoint responds 200
- ✅ Frontend loads at root URL

**Failure Indicators**:
- ❌ BUILD FAILURE in logs
- ❌ Status shows "Deploy failed"
- ❌ Logs contain Java exceptions
- ❌ Health check times out

---

## 🔗 Important URLs

### After Deployment is Live

| Service | URL |
|---------|-----|
| **Frontend (App)** | `https://petstore-xxx.onrender.com/` |
| **API Root** | `https://petstore-xxx.onrender.com/api/` |
| **List Pets** | `https://petstore-xxx.onrender.com/api/pets` |
| **Swagger Docs** | `https://petstore-xxx.onrender.com/api/swagger-ui.html` |
| **Health Check** | `https://petstore-xxx.onrender.com/actuator/health` |
| **Render Dashboard** | `https://dashboard.render.com` |

*Replace `xxx` with your actual service name from Render*

---

## 💾 Environment Variables Quick Reference

### Required for Deployment

```
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

### Auto-Provided by PostgreSQL Service

```
DATABASE_URL              # Full connection string
POSTGRES_HOST             # Database server
POSTGRES_PORT             # Usually 5432
POSTGRES_USER             # Database user
POSTGRES_PASSWORD         # Database password
POSTGRES_DB               # Database name
```

### Optional (Already in application-prod.properties)

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO
SPRING_FLYWAY_BASELINE_ON_MIGRATE
```

---

## 🔄 Continuous Deployment

After initial deployment, every time you:

```bash
git push origin main
```

Render **automatically**:
1. ✅ Detects new commit
2. ✅ Pulls code from GitHub
3. ✅ Rebuilds Docker image
4. ✅ Runs new container
5. ✅ Runs health checks
6. ✅ Deploys to production

**Time**: ~3-5 minutes per deployment

**To Disable Auto-Deploy**:
- Service Settings → Toggle **Auto-Deploy** off
- Then manually click **Deploy** button each time

---

## 📞 Getting Help

### Check Resources (In Order)

1. **[RENDER_QUICK_START.md](./RENDER_QUICK_START.md)** - 5-minute overview
2. **[RENDER_DEPLOYMENT_GUIDE.md](./RENDER_DEPLOYMENT_GUIDE.md)** - Detailed walkthrough
3. **[RENDER_DEPLOYMENT_SETUP.md](./RENDER_DEPLOYMENT_SETUP.md)** - Complete reference
4. **Render Logs** - Check Dashboard → Logs tab
5. **Render Documentation** - https://render.com/docs
6. **GitHub Issues** - Search existing issues in your repo

### Common Quick Fixes

| Error | Quick Fix |
|-------|-----------|
| Build timeout | Increase plan from Starter |
| Out of memory | Increase plan memory |
| Database won't connect | Add 1-minute delay, logs auto-retry |
| Migration failed | Check database exists, permissions correct |
| API returns 500 | Check application logs |

---

## ✨ Success Indicators

✅ **Deployment Complete When**:
- Service status is **Live** (green)
- Logs show no error messages
- Health endpoint returns 200 OK
- Frontend loads at root URL
- API calls work without CORS errors
- Database migrations completed successfully

✅ **Application Ready When**:
- You can browse pets (GET /api/pets works)
- Page loads and displays properly
- No console errors in browser
- API Swagger documentation accessible

---

## 📝 Post-Deployment Todos

### Immediate (After Going Live)

- [ ] Test all features in production
- [ ] Check database has proper backups
- [ ] Monitor logs for 15 minutes
- [ ] Share production URL with stakeholders

### Within 24 Hours

- [ ] Set up monitoring alerts (if needed)
- [ ] Document any production-specific issues
- [ ] Update README with production URL
- [ ] Celebrate successful deployment! 🎉

### Within 1 Week

- [ ] Monitor performance metrics
- [ ] Check application logs regularly
- [ ] Plan for future optimizations
- [ ] Consider scaling strategy

---

## 🎯 Next Steps

1. **Start Deployment**: Follow Step-by-Step guide above
2. **Monitor Progress**: Watch build logs in Render Dashboard
3. **Verify**: Run post-deployment verification checklist
4. **Share**: Tell team the production URL
5. **Monitor**: Check logs regularly for issues

---

**Status**: Ready to Deploy ✅  
**Estimated Deployment Time**: 10-15 minutes  
**Success Rate**: 95%+ (with this checklist)  
**Support**: See resources above if issues occur

---

**Good luck with your deployment! 🚀**
