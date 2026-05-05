# Render Deployment Guide - Petstore Application

## 📋 Prerequisites

- Render account (https://render.com)
- GitHub repository with Petstore code pushed
- Render CLI (optional, for advanced configuration)

## 🚀 Deployment Steps

### Step 1: Connect GitHub Repository

1. Log in to Render Dashboard: https://dashboard.render.com
2. Click **New +** → **Web Service**
3. Select **Build and deploy from a Git repository**
4. Connect your GitHub account (if not already connected)
5. Select the Petstore repository

### Step 2: Configure Backend Service

**Service Settings:**
- **Name**: `petstore-backend`
- **Environment**: `Docker`
- **Branch**: `main` (or your default branch)
- **Build Command**: `mvn clean package -f backend/pom.xml`
- **Start Command**: `java -jar backend/target/petstore-api-1.0.0.jar`

**Plan**: Starter (free tier available)

**Environment Variables:**
Add these via Render Dashboard:
```
PORT=8080
SPRING_PROFILES_ACTIVE=prod
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_FLYWAY_BASELINE_ON_MIGRATE=true
```

### Step 3: Create PostgreSQL Service

1. Click **New +** → **PostgreSQL**
2. **Name**: `petstore-postgres`
3. **Database**: `petstore_db`
4. **User**: `petstore_user`
5. Save - Render generates secure password

Render automatically provides:
- `DATABASE_URL` environment variable
- Secure connection string

### Step 4: Deploy Frontend

Option A: Deploy as Static Site
1. Click **New +** → **Static Site**
2. Select same GitHub repository
3. **Build Command**: `cd frontend && npm install && npm run build`
4. **Publish Directory**: `frontend/dist`
5. Add environment variable:
   ```
   VITE_API_BASE_URL=https://petstore-backend.onrender.com
   ```

Option B: Include in Docker (recommended for this project)
- Frontend is bundled with backend container
- No separate static site needed
- Single deployment to manage

### Step 5: Connect Services

In Backend environment variables, add:
```
SPRING_DATASOURCE_URL=postgresql://[postgres-service-url]/petstore_db
SPRING_DATASOURCE_USERNAME=petstore_user
SPRING_DATASOURCE_PASSWORD=[postgres-password-from-render]
```

### Step 6: Deploy & Verify

1. Click **Deploy** on backend service
2. Monitor deployment in Render dashboard
3. Check logs for any errors
4. Verify database migrations ran successfully
5. Test API: `https://petstore-backend.onrender.com/api/pets`

## 📝 Environment Variables Reference

| Variable | Value | Source |
|----------|-------|--------|
| `PORT` | `8080` | Manual |
| `SPRING_PROFILES_ACTIVE` | `prod` | Manual |
| `DATABASE_URL` | Auto-generated | PostgreSQL service |
| `SPRING_DATASOURCE_URL` | From `DATABASE_URL` | PostgreSQL service |
| `SPRING_DATASOURCE_USERNAME` | `petstore_user` | PostgreSQL service |
| `SPRING_DATASOURCE_PASSWORD` | Auto-generated | PostgreSQL service |

## 🔄 Continuous Deployment

Render automatically redeploys when:
- Push to connected branch (default: main)
- Environment variables updated
- GitHub Actions workflow completes (if configured)

To disable auto-deploy:
1. Service Settings
2. Toggle **Auto-Deploy** off

## 🛠️ Troubleshooting

### Build Fails
- Check build logs in Render dashboard
- Ensure `pom.xml` has correct path
- Verify Java 17 compatibility

### Database Connection Issues
- Confirm PostgreSQL service is running
- Check environment variables match exactly
- Verify connection string format

### Application Won't Start
- Check application logs (Real-time tab)
- Verify all required environment variables are set
- Ensure database migrations completed

### Frontend Not Loading
- Check VITE_API_BASE_URL in frontend config
- Verify backend service URL is correct
- Check browser console for errors

## 📊 Monitoring

### View Logs
1. Service dashboard → **Logs** tab
2. Filter by error level if needed
3. Real-time updates on deployment

### Check Health
1. Service dashboard → **Overview**
2. Status indicator shows if running
3. CPU/Memory usage visible

### Database Backups
1. PostgreSQL service → **Backups** tab
2. Manual backup button
3. Automatic backups can be enabled

## 🔐 Security Considerations

- **Environment Variables**: Never commit `.env.render` with real values
- **Database**: PostgreSQL service includes SSL encryption
- **Secrets**: Use Render's environment variable encryption
- **Logs**: Check regularly for security issues
- **API**: Implement rate limiting (Spring Boot can do this)

## 💰 Costs

**Free Tier Limitations:**
- Services spin down after 15 minutes of inactivity
- 750 hours/month of compute
- Suitable for development/testing

**Paid Tier:** 
- $7/month per service
- Always-on instances
- Better performance

## 📦 Deployment Checklist

- [ ] GitHub repository configured
- [ ] Backend service created
- [ ] PostgreSQL database service created
- [ ] Environment variables set
- [ ] Build succeeds without errors
- [ ] Database migrations run successfully
- [ ] API endpoints responding (GET /api/pets)
- [ ] Frontend loads and connects to backend
- [ ] CORS configured (if needed)

## 🚀 Next: Scaling

Once deployed, consider:
- Caching layer (Redis on Render)
- CDN for static assets
- Auto-scaling if traffic increases
- Custom domain setup

## References

- Render Documentation: https://render.com/docs
- Spring Boot on Render: https://render.com/docs/deploy-spring
- PostgreSQL on Render: https://render.com/docs/databases
