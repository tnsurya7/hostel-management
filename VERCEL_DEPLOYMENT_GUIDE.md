# 🚀 Vercel Deployment Guide - Hostel Management System

## 📋 Overview

This guide will help you deploy your Hostel Management System to Vercel. Since you have a full-stack application:
- **Frontend (Next.js)**: Deploy to Vercel
- **Backend (Spring Boot)**: Deploy to Railway, Render, or AWS

---

## 🎯 Deployment Strategy

### Option 1: Frontend on Vercel + Backend on Railway (Recommended)
- ✅ Free tier available
- ✅ Easy setup
- ✅ Automatic deployments
- ✅ Good for production

### Option 2: Frontend on Vercel + Backend on Render
- ✅ Free tier available
- ✅ Simple configuration
- ✅ Good performance

### Option 3: Frontend on Vercel + Backend on AWS/Heroku
- ⚠️ More complex setup
- ⚠️ May require payment

---

## 📦 Part 1: Deploy Frontend to Vercel

### Step 1: Prepare Your Repository

1. **Push to GitHub** (if not done already):
```bash
git add .
git commit -m "Prepare for Vercel deployment"
git push -u origin main
```

### Step 2: Deploy to Vercel

1. **Go to Vercel**:
   - Visit https://vercel.com
   - Sign up/Login with GitHub

2. **Import Project**:
   - Click "Add New Project"
   - Select your GitHub repository: `tnsurya7/hostel-management`
   - Click "Import"

3. **Configure Project**:
   ```
   Framework Preset: Next.js
   Root Directory: frontend
   Build Command: npm run build
   Output Directory: .next
   Install Command: npm install
   ```

4. **Add Environment Variables**:
   - Click "Environment Variables"
   - Add:
     ```
     Name: NEXT_PUBLIC_API_URL
     Value: https://your-backend-url.com
     ```
   - Note: You'll update this after deploying the backend

5. **Deploy**:
   - Click "Deploy"
   - Wait 2-3 minutes for deployment
   - Your frontend will be live at: `https://your-project.vercel.app`

---

## 🖥️ Part 2: Deploy Backend (Choose One Option)

### Option A: Deploy to Railway (Recommended)

#### Step 1: Prepare Backend for Railway

1. **Create Railway Account**:
   - Go to https://railway.app
   - Sign up with GitHub

2. **Create New Project**:
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Choose your repository
   - Select `backend` folder

3. **Configure Build**:
   - Railway will auto-detect Spring Boot
   - Build Command: `mvn clean install -DskipTests`
   - Start Command: `java -jar target/*.jar`

4. **Add Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   SERVER_PORT=8080
   JWT_SECRET=your-secret-key-here-change-this-in-production
   ```

5. **Generate Domain**:
   - Go to Settings → Networking
   - Click "Generate Domain"
   - Copy the URL (e.g., `https://your-app.railway.app`)

6. **Update CORS in Backend**:
   - Add your Vercel URL to allowed origins
   - See backend configuration section below

#### Step 2: Update Frontend with Backend URL

1. **Go to Vercel Dashboard**:
   - Select your project
   - Go to Settings → Environment Variables
   - Update `NEXT_PUBLIC_API_URL` with Railway URL
   - Redeploy

---

### Option B: Deploy to Render

#### Step 1: Create Render Account

1. **Go to Render**:
   - Visit https://render.com
   - Sign up with GitHub

2. **Create New Web Service**:
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Select `backend` folder

3. **Configure Service**:
   ```
   Name: hostel-backend
   Environment: Java
   Build Command: mvn clean install -DskipTests
   Start Command: java -jar target/*.jar
   ```

4. **Add Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   SERVER_PORT=8080
   JWT_SECRET=your-secret-key-here
   ```

5. **Deploy**:
   - Click "Create Web Service"
   - Wait for deployment
   - Copy the URL

---

## ⚙️ Backend Configuration for Production

### Update application.properties

Create `backend/src/main/resources/application-prod.properties`:

```properties
# Server Configuration
server.port=${SERVER_PORT:8080}

# Database Configuration (Use PostgreSQL for production)
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# CORS Configuration
cors.allowed.origins=${FRONTEND_URL:https://your-project.vercel.app}

# Logging
logging.level.root=INFO
logging.level.com.hostel=INFO
```

### Update SecurityConfig.java

Add CORS configuration:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Get allowed origins from environment variable
    String allowedOrigins = environment.getProperty("cors.allowed.origins", 
        "http://localhost:3000");
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
    
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## 🗄️ Database Setup for Production

### Option 1: Railway PostgreSQL (Recommended)

1. **Add PostgreSQL to Railway**:
   - In Railway project, click "New"
   - Select "Database" → "PostgreSQL"
   - Railway will create database and provide connection URL

2. **Update Backend Environment Variables**:
   ```
   DATABASE_URL=postgresql://user:password@host:port/database
   ```

3. **Add PostgreSQL Dependency** to `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

### Option 2: Render PostgreSQL

1. **Create PostgreSQL Database**:
   - In Render, click "New +" → "PostgreSQL"
   - Copy connection details

2. **Update Backend**:
   - Add connection URL to environment variables

---

## 🔐 Security Checklist

Before deploying to production:

- [ ] Change JWT secret key (use strong random string)
- [ ] Update CORS allowed origins to your Vercel URL
- [ ] Use PostgreSQL instead of H2 database
- [ ] Enable HTTPS (automatic on Vercel/Railway)
- [ ] Set strong passwords for database
- [ ] Review and update security configurations
- [ ] Enable rate limiting (optional)
- [ ] Set up monitoring and logging

---

## 📝 Step-by-Step Deployment Checklist

### Phase 1: Prepare Code
- [ ] Push code to GitHub
- [ ] Create production configuration files
- [ ] Update CORS settings
- [ ] Add PostgreSQL dependency

### Phase 2: Deploy Backend
- [ ] Create Railway/Render account
- [ ] Deploy backend
- [ ] Add PostgreSQL database
- [ ] Configure environment variables
- [ ] Test backend API endpoints
- [ ] Copy backend URL

### Phase 3: Deploy Frontend
- [ ] Create Vercel account
- [ ] Import GitHub repository
- [ ] Configure root directory as `frontend`
- [ ] Add `NEXT_PUBLIC_API_URL` environment variable
- [ ] Deploy frontend
- [ ] Test login and features

### Phase 4: Final Configuration
- [ ] Update backend CORS with Vercel URL
- [ ] Redeploy backend
- [ ] Test complete application
- [ ] Verify all features work

---

## 🧪 Testing Deployed Application

### Test Backend API

```bash
# Test health endpoint
curl https://your-backend.railway.app/actuator/health

# Test login
curl -X POST https://your-backend.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Test students endpoint (with token)
curl https://your-backend.railway.app/api/students \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Test Frontend

1. Visit your Vercel URL
2. Try logging in with demo credentials
3. Test all dashboards
4. Verify CRUD operations work
5. Check PDF download

---

## 🐛 Common Issues & Solutions

### Issue 1: CORS Error
**Error**: "Access to fetch blocked by CORS policy"

**Solution**:
1. Add your Vercel URL to backend CORS configuration
2. Redeploy backend
3. Clear browser cache

### Issue 2: API Connection Failed
**Error**: "Failed to fetch" or "Network Error"

**Solution**:
1. Verify `NEXT_PUBLIC_API_URL` is set correctly in Vercel
2. Check backend is running (visit backend URL)
3. Ensure backend URL uses HTTPS

### Issue 3: Database Connection Error
**Error**: "Unable to connect to database"

**Solution**:
1. Verify DATABASE_URL is correct
2. Check database is running
3. Ensure PostgreSQL dependency is added
4. Check database credentials

### Issue 4: Build Failed on Vercel
**Error**: Build fails during deployment

**Solution**:
1. Check build logs in Vercel dashboard
2. Verify `package.json` has all dependencies
3. Ensure `next.config.mjs` is correct
4. Try building locally first: `npm run build`

### Issue 5: Environment Variables Not Working
**Error**: API calls go to localhost

**Solution**:
1. Ensure variable name starts with `NEXT_PUBLIC_`
2. Redeploy after adding environment variables
3. Check variable is set in Vercel dashboard

---

## 💰 Cost Estimate

### Free Tier (Recommended for Testing)

**Vercel (Frontend)**:
- ✅ Free for personal projects
- ✅ Unlimited deployments
- ✅ Automatic HTTPS
- ✅ Global CDN

**Railway (Backend)**:
- ✅ $5 free credit per month
- ✅ Enough for small projects
- ✅ PostgreSQL included
- ⚠️ May need to upgrade for production

**Render (Alternative)**:
- ✅ Free tier available
- ⚠️ Spins down after inactivity
- ⚠️ Slower cold starts

### Paid Tier (For Production)

**Vercel Pro**: $20/month
- More bandwidth
- Better performance
- Team features

**Railway**: ~$10-20/month
- Dedicated resources
- Better uptime
- More database storage

---

## 🚀 Quick Deploy Commands

### Deploy Frontend to Vercel (CLI Method)

```bash
# Install Vercel CLI
npm install -g vercel

# Login to Vercel
vercel login

# Deploy frontend
cd frontend
vercel

# Follow prompts:
# - Link to existing project or create new
# - Set root directory to current folder
# - Accept default settings

# Deploy to production
vercel --prod
```

---

## 📊 Post-Deployment Monitoring

### Vercel Analytics
1. Go to Vercel Dashboard
2. Select your project
3. Click "Analytics" tab
4. View traffic, performance, errors

### Railway Logs
1. Go to Railway Dashboard
2. Select your service
3. Click "Deployments"
4. View logs and metrics

---

## 🔄 Continuous Deployment

### Automatic Deployments

**Vercel**:
- Automatically deploys on every push to `main` branch
- Preview deployments for pull requests

**Railway**:
- Automatically deploys on every push to `main` branch
- Can configure deployment triggers

### Manual Deployments

**Vercel**:
```bash
cd frontend
vercel --prod
```

**Railway**:
- Click "Deploy" in Railway dashboard
- Or push to GitHub

---

## 📞 Support Resources

### Vercel
- Documentation: https://vercel.com/docs
- Support: https://vercel.com/support
- Community: https://github.com/vercel/vercel/discussions

### Railway
- Documentation: https://docs.railway.app
- Support: https://railway.app/help
- Discord: https://discord.gg/railway

### Render
- Documentation: https://render.com/docs
- Support: https://render.com/support

---

## ✅ Final Checklist

Before going live:

- [ ] Backend deployed and accessible
- [ ] Frontend deployed and accessible
- [ ] Database configured and connected
- [ ] Environment variables set correctly
- [ ] CORS configured properly
- [ ] All features tested
- [ ] Login works for all user types
- [ ] CRUD operations work
- [ ] PDF download works
- [ ] Security settings reviewed
- [ ] Monitoring set up
- [ ] Backup strategy in place

---

## 🎉 Success!

Once deployed, your application will be accessible at:
- **Frontend**: `https://your-project.vercel.app`
- **Backend**: `https://your-backend.railway.app`

Share these URLs with your users!

---

**Last Updated**: February 15, 2026
**Status**: Ready for Deployment 🚀
