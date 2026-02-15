# Quick Setup Guide

Complete this setup in 1-2 hours!

## ⚡ Quick Start (Local Development)

### Step 1: Setup Neon Database (5 minutes)

1. Go to [https://neon.tech](https://neon.tech) and sign up
2. Create a new project: `hostel-management-db`
3. Copy your connection string from the dashboard
4. Save these details:
   ```
   Host: ep-xxxx-xxxx.us-east-2.aws.neon.tech
   Database: neondb
   Username: your_username
   Password: your_password
   ```

### Step 2: Setup Backend (10 minutes)

1. **Navigate to backend**
   ```bash
   cd backend
   ```

2. **Update `application.properties`**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://YOUR_NEON_HOST/neondb?sslmode=require
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Verify**
   - Backend: http://localhost:8080
   - Swagger: http://localhost:8080/swagger-ui.html
   - Test API: http://localhost:8080/api/students

### Step 3: Setup Frontend (5 minutes)

1. **Navigate to frontend**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Create `.env.local`**
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8080/api
   ```

4. **Run development server**
   ```bash
   npm run dev
   ```

5. **Open browser**
   - Visit: http://localhost:3000
   - Try adding a student!

## 🎯 Test Your Setup

1. **Add a student**
   - Name: Test Student
   - Email: test@example.com
   - Room: 101
   - Fees: Unchecked

2. **Verify in database**
   - Go to Neon Console → SQL Editor
   - Run: `SELECT * FROM students;`

3. **Test all features**
   - Toggle fees paid
   - Delete student
   - Add multiple students
   - Check statistics

## 🚀 Deploy to Production (30-40 minutes)

Follow the detailed [DEPLOYMENT.md](./DEPLOYMENT.md) guide.

Quick summary:
1. Push code to GitHub
2. Deploy backend to Render (15 min)
3. Deploy frontend to Vercel (10 min)
4. Test production deployment

## 📊 Database Schema

The table will be auto-created by Spring Boot, but you can manually create it:

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

## 🔍 Troubleshooting

### Backend won't start
- Check Java version: `java -version` (should be 17+)
- Verify Maven: `mvn -version`
- Check Neon DB connection string
- Look at console errors

### Frontend won't start
- Check Node version: `node -version` (should be 18+)
- Delete `node_modules` and reinstall: `rm -rf node_modules && npm install`
- Check `.env.local` file exists
- Look at console errors

### Can't connect to database
- Verify Neon DB is active (not suspended)
- Check connection string includes `?sslmode=require`
- Test connection in Neon Console
- Check firewall settings

### API calls fail
- Verify backend is running on port 8080
- Check CORS is enabled in controller
- Verify API URL in frontend `.env.local`
- Check browser console for errors

## 📝 Sample Data

Use Swagger UI to add sample data:

```json
{
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "roomNumber": "101",
  "feesPaid": true
}
```

```json
{
  "name": "Bob Smith",
  "email": "bob@example.com",
  "roomNumber": "102",
  "feesPaid": false
}
```

```json
{
  "name": "Charlie Brown",
  "email": "charlie@example.com",
  "roomNumber": "101",
  "feesPaid": false
}
```

## 🎨 Customization Ideas

1. **Add more fields**
   - Phone number
   - Address
   - Emergency contact
   - Date of admission

2. **Add features**
   - Search by name
   - Filter by room
   - Export to Excel
   - Email notifications

3. **Improve UI**
   - Add animations
   - Better mobile layout
   - Custom color scheme
   - Add charts

## ✅ Checklist

- [ ] Java 17+ installed
- [ ] Node.js 18+ installed
- [ ] Maven installed
- [ ] Neon account created
- [ ] Database connection configured
- [ ] Backend running on :8080
- [ ] Frontend running on :3000
- [ ] Can add students
- [ ] Can view students
- [ ] Can update fees
- [ ] Can delete students
- [ ] Dark mode works
- [ ] Ready to deploy!

## 🎉 Next Steps

1. Complete local testing
2. Push to GitHub
3. Deploy to production
4. Add to your resume/portfolio
5. Share with friends!

## 📚 Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Next.js Docs](https://nextjs.org/docs)
- [Neon Docs](https://neon.tech/docs)
- [Render Docs](https://render.com/docs)
- [Vercel Docs](https://vercel.com/docs)

---

Need help? Check the main [README.md](./README.md) or [DEPLOYMENT.md](./DEPLOYMENT.md)!
