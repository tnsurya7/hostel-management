# Deployment Guide

Complete step-by-step guide to deploy the Hostel Management System.

## 📋 Prerequisites

- GitHub account
- Neon PostgreSQL account
- Render account (for backend)
- Vercel account (for frontend)

## 🗄️ Step 1: Setup Neon Database

1. **Create Neon Account**
   - Visit [https://neon.tech](https://neon.tech)
   - Sign up with GitHub or email

2. **Create New Project**
   - Click "Create Project"
   - Name: `hostel-management-db`
   - Region: Choose closest to your users
   - Click "Create Project"

3. **Get Connection Details**
   - Go to Dashboard
   - Copy the connection string:
     ```
     postgresql://username:password@ep-xxxx-xxxx.us-east-2.aws.neon.tech/neondb?sslmode=require
     ```
   - Save these credentials:
     - Host: `ep-xxxx-xxxx.us-east-2.aws.neon.tech`
     - Database: `neondb`
     - Username: `your_username`
     - Password: `your_password`

4. **Create Table (Optional)**
   - Go to SQL Editor in Neon Console
   - Run:
     ```sql
     CREATE TABLE students (
         id BIGSERIAL PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         email VARCHAR(255) NOT NULL UNIQUE,
         room_number VARCHAR(50),
         fees_paid BOOLEAN DEFAULT FALSE,
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
     );
     ```
   - Note: Spring Boot will auto-create tables if `spring.jpa.hibernate.ddl-auto=update`

## 🚀 Step 2: Deploy Backend to Render

### 2.1 Prepare Repository

1. **Push to GitHub**
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/yourusername/hostel-management.git
   git push -u origin main
   ```

### 2.2 Create Render Service

1. **Login to Render**
   - Visit [https://render.com](https://render.com)
   - Sign up/Login with GitHub

2. **Create New Web Service**
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Select `hostel-management` repository

3. **Configure Service**
   ```
   Name: hostel-backend
   Region: Oregon (US West) or closest
   Branch: main
   Root Directory: backend
   Runtime: Java
   Build Command: mvn clean install -DskipTests
   Start Command: java -jar target/hostel-management-1.0.0.jar
   Instance Type: Free
   ```

4. **Add Environment Variables**
   Click "Advanced" → "Add Environment Variable":
   
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://ep-xxxx.neon.tech/neondb?sslmode=require
   SPRING_DATASOURCE_USERNAME=your_neon_username
   SPRING_DATASOURCE_PASSWORD=your_neon_password
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SERVER_PORT=8080
   ```

5. **Deploy**
   - Click "Create Web Service"
   - Wait 5-10 minutes for build
   - Once deployed, copy the URL: `https://hostel-backend-xxxx.onrender.com`

6. **Test Backend**
   - Visit: `https://hostel-backend-xxxx.onrender.com/swagger-ui.html`
   - Test API: `https://hostel-backend-xxxx.onrender.com/api/students`

### 2.3 Troubleshooting Render

**Build Fails:**
- Check Java version in `pom.xml` matches Render's Java version
- Ensure `mvn clean install` works locally
- Check build logs in Render dashboard

**Service Crashes:**
- Check environment variables are correct
- Verify Neon DB connection string
- Check application logs in Render

**CORS Issues:**
- Ensure `@CrossOrigin(origins = "*")` in controller
- Or configure specific origins in production

## 🌐 Step 3: Deploy Frontend to Vercel

### 3.1 Prepare Frontend

1. **Update Environment Variables**
   
   Edit `frontend/.env.production`:
   ```env
   NEXT_PUBLIC_API_URL=https://hostel-backend-xxxx.onrender.com/api
   ```

2. **Test Build Locally**
   ```bash
   cd frontend
   npm run build
   ```

### 3.2 Deploy to Vercel

**Option A: Vercel CLI**

1. **Install Vercel CLI**
   ```bash
   npm install -g vercel
   ```

2. **Login**
   ```bash
   vercel login
   ```

3. **Deploy**
   ```bash
   cd frontend
   vercel
   ```
   
   Follow prompts:
   - Setup and deploy? `Y`
   - Which scope? Select your account
   - Link to existing project? `N`
   - Project name? `hostel-management`
   - Directory? `./`
   - Override settings? `N`

4. **Add Environment Variable**
   ```bash
   vercel env add NEXT_PUBLIC_API_URL production
   ```
   Enter: `https://hostel-backend-xxxx.onrender.com/api`

5. **Deploy to Production**
   ```bash
   vercel --prod
   ```

**Option B: Vercel Dashboard**

1. **Login to Vercel**
   - Visit [https://vercel.com](https://vercel.com)
   - Sign up/Login with GitHub

2. **Import Project**
   - Click "Add New..." → "Project"
   - Import your GitHub repository
   - Select `hostel-management`

3. **Configure Project**
   ```
   Framework Preset: Next.js
   Root Directory: frontend
   Build Command: npm run build
   Output Directory: .next
   Install Command: npm install
   ```

4. **Add Environment Variables**
   - Click "Environment Variables"
   - Add:
     ```
     Name: NEXT_PUBLIC_API_URL
     Value: https://hostel-backend-xxxx.onrender.com/api
     Environment: Production
     ```

5. **Deploy**
   - Click "Deploy"
   - Wait 2-3 minutes
   - Copy deployment URL: `https://hostel-management-xxxx.vercel.app`

### 3.3 Test Deployment

1. Visit your Vercel URL
2. Try adding a student
3. Check if data persists
4. Test all CRUD operations

## 🔧 Step 4: Post-Deployment Configuration

### 4.1 Update CORS (Production)

For production, update backend CORS to specific origin:

```java
@CrossOrigin(origins = "https://hostel-management-xxxx.vercel.app")
```

Or in a configuration class:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://hostel-management-xxxx.vercel.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
```

### 4.2 Custom Domain (Optional)

**Vercel:**
1. Go to Project Settings → Domains
2. Add your custom domain
3. Update DNS records as instructed

**Render:**
1. Go to Service Settings → Custom Domain
2. Add your domain
3. Update DNS records

## 📊 Step 5: Monitoring

### Render Monitoring
- Dashboard → Your Service → Metrics
- View CPU, Memory, Response times
- Check logs for errors

### Vercel Monitoring
- Project → Analytics
- View page views, performance
- Check function logs

## 🔒 Step 6: Security Best Practices

1. **Environment Variables**
   - Never commit `.env` files
   - Use different credentials for dev/prod
   - Rotate passwords regularly

2. **Database**
   - Enable SSL (Neon does this by default)
   - Use strong passwords
   - Limit connection pooling

3. **API**
   - Add rate limiting
   - Implement authentication (JWT)
   - Validate all inputs

4. **Frontend**
   - Use HTTPS only
   - Implement CSP headers
   - Sanitize user inputs

## 🐛 Common Issues

### Issue: Backend not connecting to Neon
**Solution:**
- Verify connection string format
- Ensure `?sslmode=require` is included
- Check Neon DB is active (not suspended)

### Issue: Frontend can't reach backend
**Solution:**
- Check CORS configuration
- Verify API URL in environment variables
- Test backend endpoint directly

### Issue: Render service keeps crashing
**Solution:**
- Check application logs
- Verify all environment variables
- Ensure port 8080 is used
- Check memory limits

### Issue: Build fails on Render
**Solution:**
- Verify `pom.xml` is correct
- Check Java version compatibility
- Try building locally first
- Review build logs

## 📈 Scaling Tips

1. **Database**
   - Upgrade Neon plan for more connections
   - Add connection pooling
   - Implement caching (Redis)

2. **Backend**
   - Upgrade Render instance
   - Add load balancing
   - Implement CDN

3. **Frontend**
   - Vercel auto-scales
   - Optimize images
   - Implement lazy loading

## 🎉 Success Checklist

- [ ] Neon database created and accessible
- [ ] Backend deployed on Render
- [ ] Backend Swagger UI accessible
- [ ] Frontend deployed on Vercel
- [ ] Frontend can create students
- [ ] Frontend can view students
- [ ] Frontend can update students
- [ ] Frontend can delete students
- [ ] Dark mode works
- [ ] Mobile responsive
- [ ] Error handling works
- [ ] Loading states display

## 📞 Support

If you encounter issues:
1. Check application logs
2. Review this guide
3. Test locally first
4. Check service status pages
5. Contact support if needed

## 🔄 Continuous Deployment

Both Render and Vercel support automatic deployments:

1. **Push to GitHub**
   ```bash
   git add .
   git commit -m "Update feature"
   git push
   ```

2. **Auto Deploy**
   - Render: Automatically rebuilds backend
   - Vercel: Automatically rebuilds frontend

3. **Monitor**
   - Check deployment status in dashboards
   - Test changes in production

---

Congratulations! Your Hostel Management System is now live! 🎉
