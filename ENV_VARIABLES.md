# 🔐 Environment Variables Reference

## 📋 Quick Copy-Paste for Deployment

### Backend Environment Variables (Railway)

Copy and paste these into Railway → Variables:

```bash
SPRING_PROFILES_ACTIVE=vercel
DATABASE_URL=postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST-pooler.neon.tech/neondb?sslmode=require
PGHOST=YOUR_HOST-pooler.neon.tech
PGUSER=YOUR_USERNAME
PGDATABASE=neondb
PGPASSWORD=npg_RuAqosp25ZJz
JWT_SECRET=change-this-to-a-super-strong-random-string-min-256-bits
FRONTEND_URL=https://your-project.vercel.app
PORT=8080
```

⚠️ **Important**: 
- Change `JWT_SECRET` to a strong random string
- Update `FRONTEND_URL` after deploying frontend

---

### Frontend Environment Variables (Vercel)

Copy and paste this into Vercel → Environment Variables:

```bash
NEXT_PUBLIC_API_URL=https://your-backend.railway.app
```

⚠️ **Important**: Update with your actual Railway backend URL

---

## 🗄️ Database Connection Strings

### For Spring Boot (Use in Railway)
```
DATABASE_URL=postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST-pooler.neon.tech/neondb?sslmode=require
```

### For Direct Connection (psql, pgAdmin)
```
postgresql://YOUR_USERNAME:YOUR_PASSWORD@YOUR_HOST.neon.tech/neondb?sslmode=require
```

### Individual Parameters
```
Host: ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
Port: 5432
Database: neondb
User: neondb_owner
Password: npg_RuAqosp25ZJz
SSL Mode: require
```

---

## 🔑 Generate Strong JWT Secret

Use one of these methods to generate a strong JWT secret:

### Method 1: OpenSSL
```bash
openssl rand -base64 64
```

### Method 2: Node.js
```bash
node -e "console.log(require('crypto').randomBytes(64).toString('base64'))"
```

### Method 3: Online Generator
Visit: https://generate-secret.vercel.app/64

Copy the generated string and use it as `JWT_SECRET`

---

## 📝 Step-by-Step Setup

### 1. Deploy Backend to Railway

1. Go to Railway → New Project → Deploy from GitHub
2. Select your repository
3. Set Root Directory: `backend`
4. Click "Variables" tab
5. Add all backend variables (see above)
6. Click "Deploy"
7. Go to Settings → Networking → Generate Domain
8. Copy the URL (e.g., `https://hostel-backend-production.up.railway.app`)

### 2. Deploy Frontend to Vercel

1. Go to Vercel → New Project
2. Import your GitHub repository
3. Set Root Directory: `frontend`
4. Add Environment Variable:
   - Name: `NEXT_PUBLIC_API_URL`
   - Value: Your Railway URL from step 1.8
5. Click "Deploy"
6. Copy your Vercel URL (e.g., `https://hostel-management-xyz.vercel.app`)

### 3. Update Backend CORS

1. Go back to Railway
2. Click "Variables"
3. Update `FRONTEND_URL` with your Vercel URL from step 2.6
4. Railway will automatically redeploy

---

## ✅ Verification Checklist

### Backend Variables (Railway)
- [ ] SPRING_PROFILES_ACTIVE = vercel
- [ ] DATABASE_URL = (Neon PostgreSQL URL)
- [ ] PGHOST = (Neon host)
- [ ] PGUSER = neondb_owner
- [ ] PGDATABASE = neondb
- [ ] PGPASSWORD = (Neon password)
- [ ] JWT_SECRET = (Strong random string)
- [ ] FRONTEND_URL = (Your Vercel URL)
- [ ] PORT = 8080

### Frontend Variables (Vercel)
- [ ] NEXT_PUBLIC_API_URL = (Your Railway URL)

### URLs Match
- [ ] FRONTEND_URL in Railway = Your Vercel URL
- [ ] NEXT_PUBLIC_API_URL in Vercel = Your Railway URL

---

## 🧪 Test After Deployment

### Test Backend
```bash
# Replace with your Railway URL
curl https://your-backend.railway.app/actuator/health
```

Expected: `{"status":"UP"}`

### Test Database Connection
```bash
# Replace with your Railway URL
curl -X POST https://your-backend.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Expected: JWT token in response

### Test Frontend
1. Visit your Vercel URL
2. Click "Admin Demo"
3. Click "Sign In"
4. Should see admin dashboard

---

## 🔄 Update Environment Variables

### Update Backend Variable (Railway)
1. Go to Railway dashboard
2. Click on your service
3. Click "Variables"
4. Edit the variable
5. Save (Railway auto-redeploys)

### Update Frontend Variable (Vercel)
1. Go to Vercel dashboard
2. Click on your project
3. Go to Settings → Environment Variables
4. Edit the variable
5. Go to Deployments → Redeploy

---

## 🐛 Common Issues

### Issue: "DATABASE_URL not found"
**Solution**: Make sure you added all database variables in Railway

### Issue: "CORS error"
**Solution**: Verify FRONTEND_URL in Railway matches your Vercel URL exactly

### Issue: "API connection failed"
**Solution**: Check NEXT_PUBLIC_API_URL in Vercel is correct

### Issue: "Authentication failed"
**Solution**: Verify Neon database credentials are correct

---

## 📞 Quick Help

### Railway Logs
Railway Dashboard → Your Service → Deployments → View Logs

### Vercel Logs
Vercel Dashboard → Your Project → Deployments → View Function Logs

### Neon Console
https://console.neon.tech → Your Project → SQL Editor

---

**Ready to Deploy!** 🚀

Follow the steps in NEON_DEPLOYMENT_GUIDE.md for complete instructions.
