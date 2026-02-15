# 🚀 Deployment Guide with Neon PostgreSQL

## 📋 Your Database Credentials

You have a Neon PostgreSQL database ready to use!

### Database Connection Details

```
Host: ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
Database: neondb
User: neondb_owner
Password: npg_RuAqosp25ZJz
```

### Connection URLs

**Pooled (Recommended for most uses):**
```
postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST-pooler.neon.tech/neondb?sslmode=require
```

**Direct (For migrations):**
```
postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST.neon.tech/neondb?sslmode=require
```

---

## 🎯 Step 1: Deploy Backend to Railway

### 1.1 Create Railway Account
1. Go to https://railway.app
2. Sign up with GitHub
3. Click "New Project"

### 1.2 Deploy from GitHub
1. Select "Deploy from GitHub repo"
2. Choose `tnsurya7/hostel-management`
3. Railway will detect your repository

### 1.3 Configure Backend Service

**Set Root Directory:**
1. Click on your service
2. Go to "Settings" → "Build"
3. Set Root Directory: `backend`
4. Build Command: `mvn clean install -DskipTests`
5. Start Command: `java -jar target/*.jar`

### 1.4 Add Environment Variables

Click "Variables" and add these:

```bash
# Spring Profile
SPRING_PROFILES_ACTIVE=vercel

# Database Configuration (Neon PostgreSQL)
DATABASE_URL=postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST-pooler.neon.tech/neondb?sslmode=require

PGHOST=YOUR_HOST-pooler.neon.tech
PGUSER=neondb_owner
PGDATABASE=neondb
PGPASSWORD=npg_RuAqosp25ZJz

# JWT Secret (Change this to a strong random string)
JWT_SECRET=your-super-secret-jwt-key-min-256-bits-change-this-now

# Frontend URL (Update after deploying frontend)
FRONTEND_URL=https://your-project.vercel.app

# Server Port
PORT=8080
```

### 1.5 Generate Public URL
1. Go to "Settings" → "Networking"
2. Click "Generate Domain"
3. Copy the URL (e.g., `https://hostel-backend-production.up.railway.app`)
4. Save this URL - you'll need it for frontend

### 1.6 Deploy
Railway will automatically deploy your backend!

---

## 🌐 Step 2: Deploy Frontend to Vercel

### 2.1 Go to Vercel
1. Visit https://vercel.com
2. Sign in with GitHub
3. Click "Add New Project"

### 2.2 Import Repository
1. Select `tnsurya7/hostel-management`
2. Click "Import"

### 2.3 Configure Project

**Framework Preset:** Next.js (auto-detected)

**Root Directory:** `frontend` ⚠️ IMPORTANT!

**Build Settings:**
- Build Command: `npm run build`
- Output Directory: `.next`
- Install Command: `npm install`

### 2.4 Add Environment Variables

Click "Environment Variables" and add:

```bash
# Backend API URL (Use your Railway URL from Step 1.5)
NEXT_PUBLIC_API_URL=https://hostel-backend-production.up.railway.app
```

### 2.5 Deploy
Click "Deploy" and wait 2-3 minutes!

---

## 🔄 Step 3: Update Backend CORS

After frontend is deployed:

1. **Get your Vercel URL** (e.g., `https://hostel-management-xyz.vercel.app`)

2. **Go back to Railway**
   - Click on your backend service
   - Go to "Variables"
   - Update `FRONTEND_URL`:
     ```
     FRONTEND_URL=https://hostel-management-xyz.vercel.app
     ```

3. **Railway will automatically redeploy**

---

## 🗄️ Step 4: Initialize Database

Your Neon PostgreSQL database is ready, but it's empty. The backend will automatically create tables when it starts.

### Automatic Table Creation

When your backend starts on Railway:
1. Spring Boot will connect to Neon PostgreSQL
2. Hibernate will automatically create all 22 tables
3. `DataInitializationService` will load fake data

### Verify Database

You can connect to your Neon database using:

**Using psql:**
```bash
psql "postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST-pooler.neon.tech/neondb?sslmode=require"
```

**Using Neon Console:**
1. Go to https://console.neon.tech
2. Login with your account
3. Select your project
4. Click "SQL Editor"
5. Run queries to check tables

**Check Tables:**
```sql
-- List all tables
SELECT table_name FROM information_schema.tables 
WHERE table_schema = 'public';

-- Check users
SELECT * FROM users;

-- Check students
SELECT * FROM students;
```

---

## 🧪 Step 5: Test Your Deployment

### Test Backend
```bash
# Health check
curl https://your-backend.railway.app/actuator/health

# Login test
curl -X POST https://your-backend.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Test Frontend
1. Visit your Vercel URL
2. Click "Admin Demo"
3. Click "Sign In"
4. Should see admin dashboard with data

---

## 📝 Environment Variables Summary

### Backend (Railway)

| Variable | Value |
|----------|-------|
| SPRING_PROFILES_ACTIVE | vercel |
| DATABASE_URL | postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST-pooler.neon.tech/neondb?sslmode=require |
| PGHOST | YOUR_HOST-pooler.neon.tech |
| PGUSER | YOUR_USERNAME |
| PGDATABASE | neondb |
| PGPASSWORD | npg_RuAqosp25ZJz |
| JWT_SECRET | your-super-secret-jwt-key-change-this |
| FRONTEND_URL | https://your-project.vercel.app |
| PORT | 8080 |

### Frontend (Vercel)

| Variable | Value |
|----------|-------|
| NEXT_PUBLIC_API_URL | https://your-backend.railway.app |

---

## 🔒 Security Checklist

Before going live:

- [ ] Change JWT_SECRET to a strong random string (min 256 bits)
- [ ] Update FRONTEND_URL with your actual Vercel URL
- [ ] Verify CORS is working (test login from frontend)
- [ ] Check database connection is secure (SSL enabled)
- [ ] Review Neon database access settings
- [ ] Enable Neon database backups
- [ ] Set up monitoring

---

## 🐛 Troubleshooting

### Issue: Backend won't start
**Check Railway logs:**
1. Go to Railway dashboard
2. Click on your service
3. Click "Deployments"
4. View logs

**Common causes:**
- DATABASE_URL not set correctly
- PostgreSQL driver not found (should be in pom.xml)
- Port conflict

**Solution:**
- Verify all environment variables are set
- Check `application-vercel.properties` exists
- Ensure `SPRING_PROFILES_ACTIVE=vercel`

### Issue: Database connection failed
**Error:** "Connection refused" or "Authentication failed"

**Solution:**
1. Verify DATABASE_URL is correct
2. Check Neon database is active (go to Neon console)
3. Ensure SSL is enabled (`?sslmode=require`)
4. Check firewall settings in Neon

### Issue: Tables not created
**Solution:**
1. Check `spring.jpa.hibernate.ddl-auto=update` in properties
2. View Railway logs for Hibernate errors
3. Manually create tables if needed (see SQL below)

### Issue: CORS error on frontend
**Error:** "Access blocked by CORS policy"

**Solution:**
1. Verify FRONTEND_URL is set in Railway
2. Check it matches your Vercel URL exactly
3. Redeploy backend after updating

### Issue: Frontend can't connect to backend
**Error:** "Failed to fetch" or "Network error"

**Solution:**
1. Verify NEXT_PUBLIC_API_URL in Vercel
2. Check backend is running (visit Railway URL)
3. Ensure backend URL uses HTTPS
4. Redeploy frontend after updating env var

---

## 📊 Database Schema

Your Neon PostgreSQL database will have these tables:

```
users, user_roles, students, rooms, beds, room_allocations,
room_maintenance, room_change_requests, fee_types, fee_payments,
visitors, complaints, attendance, mess_menu, notifications,
gate_passes, documents, inventory_items, item_allocations,
emergency_alerts, emergency_contacts, laundry_requests,
maintenance_schedules, messages, announcements, leave_requests,
parent_access, parent_dashboard, student_dashboard
```

---

## 🔄 Continuous Deployment

### Automatic Deployments

**Backend (Railway):**
- Push to GitHub → Railway automatically deploys
- No manual steps needed

**Frontend (Vercel):**
- Push to GitHub → Vercel automatically deploys
- Preview deployments for pull requests

### Manual Redeploy

**Railway:**
1. Go to Railway dashboard
2. Click "Deployments"
3. Click "Redeploy"

**Vercel:**
1. Go to Vercel dashboard
2. Click "Deployments"
3. Click "Redeploy"

---

## 💰 Cost Estimate

### Neon PostgreSQL
- **Free Tier**: 0.5 GB storage, 1 compute hour
- **Pro**: $19/month for more storage and compute
- Your database is already created!

### Railway
- **Free**: $5 credit/month
- **Pro**: ~$10-20/month for production

### Vercel
- **Free**: Unlimited for personal projects
- **Pro**: $20/month for teams

**Total Free Tier**: $0-5/month
**Total Production**: ~$30-50/month

---

## ✅ Deployment Checklist

### Pre-Deployment
- [x] Neon PostgreSQL database created
- [x] Database credentials obtained
- [x] Backend configuration files created
- [x] Frontend environment variables configured
- [ ] Code pushed to GitHub

### Backend Deployment
- [ ] Railway account created
- [ ] Backend deployed to Railway
- [ ] Environment variables added
- [ ] Public URL generated
- [ ] Database connection verified
- [ ] Tables created automatically
- [ ] Fake data loaded

### Frontend Deployment
- [ ] Vercel account created
- [ ] Frontend deployed to Vercel
- [ ] Root directory set to `frontend`
- [ ] NEXT_PUBLIC_API_URL added
- [ ] Deployment successful
- [ ] Site accessible

### Post-Deployment
- [ ] Backend CORS updated with Vercel URL
- [ ] Login tested from frontend
- [ ] All features working
- [ ] Database queries working
- [ ] JWT_SECRET changed to strong value

---

## 🎉 Success!

Once deployed, your application will be:
- ✅ Running on Vercel (frontend)
- ✅ Running on Railway (backend)
- ✅ Connected to Neon PostgreSQL (database)
- ✅ Fully functional with all 20 features
- ✅ Accessible worldwide via HTTPS

**Your URLs:**
- Frontend: `https://your-project.vercel.app`
- Backend: `https://your-backend.railway.app`
- Database: Neon PostgreSQL (managed)

---

**Last Updated**: February 15, 2026
**Status**: Ready for Deployment with Neon PostgreSQL! 🚀
