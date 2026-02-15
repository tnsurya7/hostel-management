# Complete Deployment Guide - Hostel Management System

## Overview
This guide will help you deploy the Hostel Management System with:
- **Frontend**: Vercel (Next.js)
- **Backend**: Railway (Spring Boot)
- **Database**: Neon PostgreSQL

---

## Prerequisites
- GitHub account with code pushed
- Vercel account (free tier works)
- Railway account (free tier works)
- Neon PostgreSQL database (already created)

---

## Part 1: Deploy Backend to Railway

### Step 1: Create Railway Project
1. Go to [Railway.app](https://railway.app)
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Connect your GitHub account and select `hostel-management` repository
5. Railway will detect the Spring Boot application

### Step 2: Configure Build Settings
1. In Railway dashboard, go to your project
2. Click on "Settings"
3. Set the following:
   - **Root Directory**: `backend`
   - **Build Command**: `mvn clean install -DskipTests`
   - **Start Command**: `java -jar target/hostel-management-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`

### Step 3: Add Environment Variables
In Railway Settings → Variables, add:

```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
JAVA_TOOL_OPTIONS=-Xmx512m
```

### Step 4: Get Backend URL
1. After deployment, Railway will provide a URL like: `https://your-app.railway.app`
2. Copy this URL - you'll need it for frontend configuration

---

## Part 2: Deploy Frontend to Vercel

### Step 1: Create Vercel Project
1. Go to [Vercel.com](https://vercel.com)
2. Click "Add New" → "Project"
3. Import your `hostel-management` repository from GitHub

### Step 2: Configure Build Settings
**IMPORTANT**: Set these before deploying:

1. **Framework Preset**: Next.js
2. **Root Directory**: `frontend` (click "Edit" and set this)
3. **Build Command**: `npm run build` (default)
4. **Output Directory**: `.next` (default)
5. **Install Command**: `npm install` (default)

### Step 3: Add Environment Variables
In Vercel Project Settings → Environment Variables, add:

```
NEXT_PUBLIC_API_URL=https://your-backend-url.railway.app
```

Replace `your-backend-url.railway.app` with the Railway URL from Part 1, Step 4.

### Step 4: Deploy
1. Click "Deploy"
2. Wait for build to complete
3. Vercel will provide a URL like: `https://your-project.vercel.app`

---

## Part 3: Update CORS Configuration

### Step 1: Update Backend CORS
1. Go back to Railway dashboard
2. Add environment variable:
   ```
   CORS_ALLOWED_ORIGINS=https://your-project.vercel.app
   ```
3. Replace with your actual Vercel URL
4. Railway will automatically redeploy

### Step 2: Update application-prod.properties (Optional)
If you want to hardcode the CORS origin:
1. Edit `backend/src/main/resources/application-prod.properties`
2. Update line:
   ```properties
   cors.allowed.origins=https://your-actual-project.vercel.app
   ```
3. Commit and push to GitHub
4. Railway will auto-deploy

---

## Part 4: Initialize Database

### Option A: Automatic Initialization (Recommended)
The `DataInitializationService` will automatically create fake data on first startup.

### Option B: Manual SQL Initialization
If you need to run SQL manually:

1. Connect to Neon PostgreSQL using the connection string:
   ```
   postgresql://neondb_owner:npg_RuAqosp25ZJz@ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require
   ```

2. Use a PostgreSQL client (pgAdmin, DBeaver, or Neon SQL Editor)

3. The tables will be auto-created by Hibernate on first run

---

## Part 5: Test Deployment

### Step 1: Test Backend
1. Open: `https://your-backend-url.railway.app/api/students`
2. You should see JSON response (might need authentication)

### Step 2: Test Frontend
1. Open: `https://your-project.vercel.app`
2. You should see the login page

### Step 3: Test Login
Use these demo credentials:

**Admin:**
- Username: `admin`
- Password: `admin123`

**Student:**
- Username: `student`
- Password: `student123`

**Parent:**
- Username: `parent`
- Password: `parent123`

---

## Part 6: Troubleshooting

### Frontend Issues

**Build Error: "No Next.js version detected"**
- Solution: Make sure Root Directory is set to `frontend` in Vercel settings

**API calls failing (CORS errors)**
- Check `NEXT_PUBLIC_API_URL` is set correctly in Vercel
- Verify Railway backend URL is accessible
- Check CORS configuration in Railway environment variables

**Environment variable not working**
- Redeploy after adding environment variables
- Check variable name starts with `NEXT_PUBLIC_` for client-side access

### Backend Issues

**Build failing on Railway**
- Check Java version (should be 21)
- Verify `backend` is set as Root Directory
- Check build logs for specific errors

**Database connection errors**
- Verify Neon PostgreSQL credentials in `application-prod.properties`
- Check if Neon database is active (not paused)
- Verify SSL mode is set to `require`

**Application not starting**
- Check Railway logs for errors
- Verify `SPRING_PROFILES_ACTIVE=prod` is set
- Check memory limits (increase if needed)

### Database Issues

**Tables not created**
- Check `spring.jpa.hibernate.ddl-auto=update` in properties
- Verify database connection is successful
- Check Railway logs for Hibernate errors

**No data in database**
- `DataInitializationService` runs on startup
- Check Railway logs to see if initialization ran
- Manually trigger by restarting the Railway service

---

## Part 7: Post-Deployment Configuration

### Update Frontend URL in Backend
If you need to update CORS after deployment:

1. Railway Dashboard → Variables
2. Update `CORS_ALLOWED_ORIGINS` with your Vercel URL
3. Railway will auto-redeploy

### Update Backend URL in Frontend
If backend URL changes:

1. Vercel Dashboard → Settings → Environment Variables
2. Update `NEXT_PUBLIC_API_URL`
3. Redeploy from Vercel dashboard

### Custom Domain (Optional)
**Vercel:**
1. Go to Project Settings → Domains
2. Add your custom domain
3. Follow DNS configuration instructions

**Railway:**
1. Go to Project Settings → Domains
2. Add custom domain
3. Update DNS records as instructed

---

## Environment Variables Summary

### Frontend (Vercel)
```
NEXT_PUBLIC_API_URL=https://your-backend-url.railway.app
```

### Backend (Railway)
```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
CORS_ALLOWED_ORIGINS=https://your-project.vercel.app
JAVA_TOOL_OPTIONS=-Xmx512m
```

### Database (Neon - Already Configured)
```
Host: ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
Database: neondb
User: neondb_owner
Password: npg_RuAqosp25ZJz
```

---

## Quick Commands

### Local Development
```bash
# Backend
cd backend
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Frontend
cd frontend
npm install
npm run dev
```

### Build for Production
```bash
# Backend
cd backend
mvn clean install -DskipTests

# Frontend
cd frontend
npm run build
```

---

## Support & Resources

- **Railway Docs**: https://docs.railway.app
- **Vercel Docs**: https://vercel.com/docs
- **Neon Docs**: https://neon.tech/docs
- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **Next.js Docs**: https://nextjs.org/docs

---

## Success Checklist

- [ ] Backend deployed to Railway
- [ ] Frontend deployed to Vercel
- [ ] Environment variables configured
- [ ] CORS configured correctly
- [ ] Database connected to Neon PostgreSQL
- [ ] Can login with demo credentials
- [ ] All pages loading correctly
- [ ] API calls working (check browser console)
- [ ] Student can create leave requests
- [ ] Admin can approve/reject requests
- [ ] Parent can view child's data

---

## Next Steps

1. Test all features thoroughly
2. Update demo credentials for production
3. Configure custom domains (optional)
4. Set up monitoring and logging
5. Configure backup strategy for database
6. Add production-ready JWT secret
7. Enable HTTPS everywhere
8. Set up CI/CD pipeline (optional)

---

**Deployment Complete!** 🎉

Your Hostel Management System is now live and ready to use.
