# Quick Start: Render Deployment

## 📋 Files Created

✅ **render.yaml** - Declarative Render service configuration  
✅ **Dockerfile** - Multi-stage build for bundled deployment  
✅ **.env.render** - Environment variables template  
✅ **application-prod.properties** - Spring Boot production profile  
✅ **.dockerignore** - Optimize Docker build context  
✅ **RENDER_DEPLOYMENT_GUIDE.md** - Step-by-step deployment instructions  

## 🚀 Deployment Architecture

```
┌─────────────────────────────────────────────────┐
│          Render.com Platform                    │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌──────────────────┐    ┌──────────────────┐  │
│  │  petstore-web    │    │  petstore-db     │  │
│  │  (Java/Docker)   │◄───│  (PostgreSQL)    │  │
│  │                  │    │                  │  │
│  │  Backend:        │    │  Managed by      │  │
│  │  :8080           │    │  Render          │  │
│  │                  │    │                  │  │
│  │  Frontend:       │    │  Backup &        │  │
│  │  /static         │    │  Restore         │  │
│  └──────────────────┘    └──────────────────┘  │
│         ▲                                       │
└────────┼───────────────────────────────────────┘
         │
      HTTPS
         │
    ┌────▼─────┐
    │ Client   │
    │ Browser  │
    └──────────┘
```

## ⚡ Quick Deploy (3 Steps)

### 1️⃣ Push to GitHub
```bash
git add .
git commit -m "Add Render deployment configuration"
git push origin main
```

### 2️⃣ Connect to Render
1. Go to https://dashboard.render.com
2. Click **New +** → **Web Service**
3. Select GitHub repository
4. Settings will auto-detect from `render.yaml` (if using YAML flow) OR:
   - Build: `cd backend && mvn clean package`
   - Start: `java -jar backend/target/petstore-api-1.0.0.jar`

### 3️⃣ Set Environment Variables
In Render Dashboard → Service → Environment:
```
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

**Database vars auto-added** from PostgreSQL service

## 📦 What's Deployed

| Component | Location | Tech | Status |
|-----------|----------|------|--------|
| **Backend API** | petstore-backend.onrender.com | Java 17 + Spring Boot | ✅ In Container |
| **Frontend** | /static (served by backend) | React 18 + Vite | ✅ In Container |
| **Database** | PostgreSQL Service | PostgreSQL 14+ | ✅ Managed |

## 🔗 Access Deployed App

Once deployment completes:

- **API Base**: `https://petstore-[service-name].onrender.com/api`
- **Get All Pets**: `https://petstore-[service-name].onrender.com/api/pets`
- **Swagger Docs**: `https://petstore-[service-name].onrender.com/api/swagger-ui.html`
- **Frontend**: `https://petstore-[service-name].onrender.com/`

## ⚙️ Configuration Files

### `render.yaml` (Declarative)
- Defines all services (web, database)
- Auto-scales configuration
- Environment variables
- Build/start commands

### `Dockerfile` (Multi-stage)
- Stage 1: Build backend JAR with Maven
- Stage 2: Build frontend with npm
- Stage 3: Runtime on OpenJDK 17
- Serves frontend from `/static` folder
- Includes health check

### `application-prod.properties`
- Database connection pooling
- Logging optimized for production
- Static resource caching
- CORS configuration
- Actuator health endpoints

### `.env.render`
- Template for all required env vars
- Documentation for each setting
- Database connection info
- API configuration

## 🛠️ Troubleshooting

### Build Fails
```bash
# Check logs in Render Dashboard
# Ensure Maven can build locally:
cd backend && mvn clean package
```

### Database Connection Error
```bash
# Verify env variables are set correctly
# Database URL format: postgresql://user:password@host:port/dbname
```

### Frontend Not Loading
```bash
# Check browser console for API errors
# Verify VITE_API_BASE_URL matches deployed backend URL
```

### Stuck in "Building" State
1. Cancel deployment
2. Clear build cache
3. Redeploy

## 📊 Monitoring

1. **Render Dashboard** → Service Overview
   - CPU, Memory, Storage usage
   - Build/deployment logs
   - Restart service if needed

2. **Health Check** (auto-configured)
   - Endpoint: `https://petstore-backend.onrender.com/actuator/health`
   - Auto-restarts if unhealthy

3. **Logs**
   - Real-time streaming
   - Filter by error level
   - Download for analysis

## 💾 Database Backups

Render PostgreSQL includes:
- ✅ Automatic daily backups
- ✅ Point-in-time restore
- ✅ Manual backup button

Access via: Render Dashboard → PostgreSQL Service → Backups

## 🔐 Security

- ✅ HTTPS auto-enabled
- ✅ PostgreSQL encrypted
- ✅ Environment variables encrypted
- ✅ No hardcoded secrets

**Best Practice**: Never commit `.env.render` with real values

## 📈 Next Steps

After successful deployment:

1. **Test API**
   ```bash
   curl https://petstore-[service].onrender.com/api/pets
   ```

2. **Monitor Logs**
   - Check Render Dashboard regularly
   - Set up alerting (if needed)

3. **Scale Up**
   - Upgrade plan from Starter to Pro
   - Enable always-on instances
   - Add caching with Redis

4. **Custom Domain**
   - Add domain in Render settings
   - Configure DNS records
   - Enable auto-renewing SSL

## 📚 Full Guide

See: `RENDER_DEPLOYMENT_GUIDE.md` for detailed instructions

## ✨ Key Features of This Setup

✅ **Zero Docker Knowledge Required** - Dockerfile handles complexity  
✅ **Managed PostgreSQL** - No manual database setup  
✅ **Automatic Scaling** - Render handles load  
✅ **Free Tier Available** - Start at $0  
✅ **HTTPS by Default** - Secure connections  
✅ **CI/CD Ready** - Auto-redeploy on git push  
✅ **Production Optimized** - Caching, compression, pooling  

---

**Status**: ✅ Ready to Deploy  
**Deployment Time**: ~5 minutes  
**Next Action**: Push to GitHub and connect Render
