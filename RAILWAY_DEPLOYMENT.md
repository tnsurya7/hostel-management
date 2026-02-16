# Deploy Backend to Railway

## Step 1: Create Railway Account
1. Go to [Railway.app](https://railway.app)
2. Sign up with GitHub
3. Click "New Project"

## Step 2: Deploy from GitHub

1. Click "Deploy from GitHub repo"
2. Select your repository: `tnsurya7/hostel-management`
3. Click "Deploy Now"

## Step 3: Configure Root Directory

1. In Railway dashboard, go to Settings
2. Set "Root Directory" to: `backend`
3. Set "Build Command" to: `mvn clean package -DskipTests`
4. Set "Start Command" to: `java -jar target/*.jar`

## Step 4: Add Environment Variables

Click on "Variables" tab and add:

```
DATABASE_URL=jdbc:postgresql://ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require
PGUSER=neondb_owner
PGPASSWORD=npg_RuAqosp25ZJz
PGHOST=ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
PGDATABASE=neondb
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits
CORS_ALLOWED_ORIGINS=https://hostel-management-five-alpha.vercel.app
PORT=8080
```

## Step 5: Get Railway URL

1. After deployment, Railway will give you a URL like:
   `https://your-app.railway.app`
2. Copy this URL

## Step 6: Update Vercel Environment Variable

1. Go to [Vercel Dashboard](https://vercel.com/dashboard)
2. Select your project: `hostel-management-five-alpha`
3. Go to Settings → Environment Variables
4. Add/Update:
   - Key: `NEXT_PUBLIC_API_URL`
   - Value: `https://your-app.railway.app` (your Railway URL)
5. Redeploy frontend

## Step 7: Test

1. Visit: https://hostel-management-five-alpha.vercel.app/login
2. Try logging in with demo credentials:
   - Admin: admin / admin123
   - Student: student / student123

## Troubleshooting

### Build fails on Railway:
- Check if `pom.xml` is in the backend folder
- Verify Java version in `pom.xml` matches Railway's Java version
- Check Railway logs for specific errors

### CORS errors:
- Make sure `CORS_ALLOWED_ORIGINS` includes your Vercel URL
- No trailing slash in the URL

### Database connection fails:
- Verify all database environment variables are set
- Test connection to Neon database separately

### 404 errors:
- Check that backend is running on Railway
- Verify the Railway URL is correct in Vercel
- Check API endpoints are correct

## Alternative: Render.com

If Railway doesn't work, you can also use Render.com:

1. Go to [Render.com](https://render.com)
2. Create "New Web Service"
3. Connect GitHub repository
4. Set Root Directory: `backend`
5. Build Command: `mvn clean package -DskipTests`
6. Start Command: `java -jar target/*.jar`
7. Add same environment variables
