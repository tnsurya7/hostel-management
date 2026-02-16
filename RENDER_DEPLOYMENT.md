# Deploy Backend to Render.com

## Step 1: Create Render Account

1. Go to [Render.com](https://render.com)
2. Sign up with GitHub
3. Verify your email

## Step 2: Create New Web Service

1. Click "New +" button (top right)
2. Select "Web Service"
3. Click "Build and deploy from a Git repository"
4. Click "Connect" next to your GitHub account
5. Find and select: `tnsurya7/hostel-management`
6. Click "Connect"

## Step 3: Configure Service

Fill in these settings:

### Basic Settings:
- **Name**: `hostel-management-backend` (or any name you prefer)
- **Region**: Choose closest to you (e.g., Oregon USA)
- **Branch**: `main`
- **Root Directory**: `backend`
- **Runtime**: `Java`

### Build Settings:
- **Build Command**: 
  ```
  mvn clean package -DskipTests
  ```

- **Start Command**:
  ```
  java -Dserver.port=$PORT -jar target/*.jar
  ```

### Instance Type:
- Select **Free** (for testing)
- Note: Free tier sleeps after 15 minutes of inactivity

## Step 4: Add Environment Variables

Click "Advanced" and add these environment variables:

```
DATABASE_URL=jdbc:postgresql://ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require

PGUSER=neondb_owner

PGPASSWORD=npg_RuAqosp25ZJz

PGHOST=ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech

PGDATABASE=neondb

SPRING_PROFILES_ACTIVE=prod

JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits

CORS_ALLOWED_ORIGINS=https://hostel-management-five-alpha.vercel.app
```

## Step 5: Deploy

1. Click "Create Web Service"
2. Wait for deployment (5-10 minutes)
3. Watch the logs for any errors

## Step 6: Get Your Render URL

After deployment succeeds, you'll get a URL like:
```
https://hostel-management-backend.onrender.com
```

Copy this URL!

## Step 7: Update Vercel Frontend

1. Go to [Vercel Dashboard](https://vercel.com/dashboard)
2. Select your project: `hostel-management-five-alpha`
3. Go to **Settings** → **Environment Variables**
4. Add or update:
   - **Key**: `NEXT_PUBLIC_API_URL`
   - **Value**: `https://hostel-management-backend.onrender.com` (your Render URL)
   - **Environment**: Production
5. Click "Save"

## Step 8: Redeploy Frontend

1. Go to **Deployments** tab in Vercel
2. Click the three dots (...) on the latest deployment
3. Click "Redeploy"
4. Wait for redeployment to complete

## Step 9: Test Your Application

1. Visit: https://hostel-management-five-alpha.vercel.app/login
2. Try logging in with demo credentials:
   - **Admin**: admin / admin123
   - **Student**: student / student123
   - **Parent**: parent / parent123

## Important Notes

### Free Tier Limitations:
- Service sleeps after 15 minutes of inactivity
- First request after sleep takes 30-60 seconds to wake up
- 750 hours/month free (enough for one service)

### If First Login is Slow:
This is normal! The free tier service was sleeping. Just wait 30-60 seconds and try again.

### To Keep Service Awake:
You can use a service like [UptimeRobot](https://uptimerobot.com) to ping your backend every 10 minutes.

## Troubleshooting

### Build Fails:

**Error: "Maven not found"**
- Make sure Runtime is set to "Java"
- Check Root Directory is set to `backend`

**Error: "pom.xml not found"**
- Verify Root Directory is exactly: `backend` (no trailing slash)

**Error: "Tests failed"**
- Build command should include `-DskipTests`

### Deployment Succeeds but App Crashes:

**Check logs for:**
- Database connection errors → Verify environment variables
- Port binding errors → Make sure Start Command includes `-Dserver.port=$PORT`
- Missing dependencies → Check pom.xml

### CORS Errors in Browser:

1. Check `CORS_ALLOWED_ORIGINS` in Render includes your Vercel URL
2. Make sure there's no trailing slash
3. Redeploy backend after changing environment variables

### 404 Errors:

- Verify backend is running (check Render dashboard)
- Test backend directly: `https://your-app.onrender.com/api/auth/login`
- Check Vercel has correct `NEXT_PUBLIC_API_URL`

### Database Connection Fails:

Test connection manually:
```bash
psql "postgresql://neondb_owner:npg_RuAqosp25ZJz@ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require" -c "SELECT version();"
```

If this fails, your Neon database might be paused or credentials are wrong.

## Monitoring Your Service

### View Logs:
1. Go to Render dashboard
2. Click on your service
3. Click "Logs" tab
4. Watch for errors or issues

### Check Service Status:
- Green dot = Running
- Yellow dot = Deploying
- Red dot = Failed

### Manual Redeploy:
1. Go to your service in Render
2. Click "Manual Deploy" → "Deploy latest commit"

## Upgrade Options

If you need better performance:

### Starter Plan ($7/month):
- No sleeping
- Always available
- Better performance

### Standard Plan ($25/month):
- More resources
- Better for production

## Alternative: Use Docker

If you have issues, you can also deploy using Docker:

1. Create `Dockerfile` in backend folder
2. Set Runtime to "Docker" in Render
3. Render will build and deploy automatically

## Next Steps After Deployment

1. ✅ Backend deployed on Render
2. ✅ Frontend updated with backend URL
3. ✅ Test all features work
4. 🔄 Consider upgrading to paid plan for production
5. 🔄 Set up monitoring/alerts
6. 🔄 Reset Neon database password for security

## Quick Reference

**Render Dashboard**: https://dashboard.render.com
**Your Backend**: https://hostel-management-backend.onrender.com
**Your Frontend**: https://hostel-management-five-alpha.vercel.app
**Neon Console**: https://console.neon.tech

## Need Help?

- Render Docs: https://render.com/docs
- Render Community: https://community.render.com
- Check logs in Render dashboard for specific errors
