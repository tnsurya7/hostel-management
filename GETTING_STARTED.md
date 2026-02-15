# Getting Started - Complete Guide

Welcome! This guide will help you set up and run the Hostel Management System in under 30 minutes.

## 📋 What You'll Build

A full-stack hostel management application with:
- Modern React frontend (Next.js 14)
- Robust Java backend (Spring Boot)
- Cloud PostgreSQL database (Neon)
- Professional UI with dark mode
- Complete CRUD operations
- Real-time statistics

## ✅ Prerequisites Checklist

Before starting, ensure you have:

- [ ] **Java 17 or higher** - [Download](https://adoptium.net/)
  ```bash
  java -version  # Should show 17 or higher
  ```

- [ ] **Maven** - [Download](https://maven.apache.org/download.cgi)
  ```bash
  mvn -version
  ```

- [ ] **Node.js 18 or higher** - [Download](https://nodejs.org/)
  ```bash
  node -v  # Should show v18 or higher
  ```

- [ ] **Git** - [Download](https://git-scm.com/)
  ```bash
  git --version
  ```

- [ ] **Code Editor** - VS Code recommended

## 🚀 Step-by-Step Setup

### Step 1: Get the Code (2 minutes)

If you have the project files:
```bash
cd hostel-management
```

If starting from scratch, the project structure is already created!

### Step 2: Setup Database (5 minutes)

1. **Create Neon Account**
   - Visit [https://neon.tech](https://neon.tech)
   - Click "Sign Up" (use GitHub for quick signup)

2. **Create Database**
   - Click "Create Project"
   - Project name: `hostel-management-db`
   - Region: Choose closest to you
   - Click "Create Project"

3. **Get Connection Details**
   - You'll see a connection string like:
     ```
     postgresql://username:password@ep-xxxx-xxxx.us-east-2.aws.neon.tech/neondb?sslmode=require
     ```
   - Keep this tab open, you'll need it!

4. **Optional: Create Table**
   - Click "SQL Editor" in Neon dashboard
   - Copy contents from `DATABASE.sql` file
   - Run the CREATE TABLE statement
   - Note: Spring Boot will auto-create the table if you skip this

### Step 3: Configure Backend (3 minutes)

1. **Open Backend Config**
   ```bash
   cd backend
   ```

2. **Edit `src/main/resources/application.properties`**
   
   Replace these lines with your Neon credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://YOUR_NEON_HOST/neondb?sslmode=require
   spring.datasource.username=YOUR_NEON_USERNAME
   spring.datasource.password=YOUR_NEON_PASSWORD
   ```

   Example:
   ```properties
   spring.datasource.url=jdbc:postgresql://ep-cool-sound-12345.us-east-2.aws.neon.tech/neondb?sslmode=require
   spring.datasource.username=neondb_owner
   spring.datasource.password=npg_abc123xyz
   ```

3. **Save the file**

### Step 4: Run Backend (5 minutes)

1. **Build the project**
   ```bash
   mvn clean install
   ```
   
   This will:
   - Download dependencies (first time takes 2-3 minutes)
   - Compile Java code
   - Run tests
   - Create JAR file

2. **Start the server**
   ```bash
   mvn spring-boot:run
   ```

3. **Verify it's running**
   - You should see: `Started HostelManagementApplication`
   - Open browser: http://localhost:8080/swagger-ui.html
   - You should see the API documentation!

4. **Test the API**
   - In Swagger UI, try "GET /api/students"
   - Click "Try it out" → "Execute"
   - You should see an empty array `[]` (no students yet)

### Step 5: Configure Frontend (2 minutes)

1. **Open new terminal** (keep backend running)

2. **Navigate to frontend**
   ```bash
   cd frontend
   ```

3. **Create environment file**
   ```bash
   # The .env.local file should already exist
   # Verify it contains:
   cat .env.local
   ```
   
   Should show:
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8080/api
   ```

4. **Install dependencies**
   ```bash
   npm install
   ```
   
   This takes 1-2 minutes.

### Step 6: Run Frontend (2 minutes)

1. **Start development server**
   ```bash
   npm run dev
   ```

2. **Open browser**
   - Visit: http://localhost:3000
   - You should see the Hostel Management System dashboard!

3. **Test the application**
   - Click "Add New Student"
   - Fill in the form:
     - Name: Test Student
     - Email: test@example.com
     - Room: 101
     - Fees Paid: Check the box
   - Click "Add Student"
   - You should see the student in the table!

## 🎉 Success! You're Running!

You now have:
- ✅ Backend running on http://localhost:8080
- ✅ Frontend running on http://localhost:3000
- ✅ Database connected to Neon
- ✅ Full CRUD operations working

## 🧪 Test All Features

1. **Add Multiple Students**
   - Add 3-4 students with different details
   - Try different room numbers
   - Mix paid and unpaid fees

2. **View Statistics**
   - Check the stats cards at the top
   - Total Students should update
   - Unpaid Fees count should update

3. **Toggle Fees**
   - Click on "Paid" or "Unpaid" badge
   - Status should toggle
   - Stats should update

4. **Delete Student**
   - Click trash icon
   - Confirm deletion
   - Student should disappear

5. **Test Dark Mode**
   - Click sun/moon icon in header
   - Theme should switch
   - Preference is saved

6. **Test Responsive Design**
   - Resize browser window
   - Try on mobile device
   - Layout should adapt

## 📚 Next Steps

### Learn the Codebase

1. **Backend Structure**
   ```
   backend/src/main/java/com/hostel/
   ├── controller/    # API endpoints
   ├── service/       # Business logic
   ├── repository/    # Database queries
   ├── model/         # Data models
   └── exception/     # Error handling
   ```

2. **Frontend Structure**
   ```
   frontend/src/
   ├── app/           # Pages
   ├── components/    # UI components
   ├── lib/           # API & utilities
   └── types/         # TypeScript types
   ```

### Explore the API

1. **Open Swagger UI**
   - http://localhost:8080/swagger-ui.html

2. **Try Each Endpoint**
   - Create student
   - Get all students
   - Update student
   - Delete student
   - Get statistics

### Customize the Application

1. **Add New Fields**
   - Phone number
   - Address
   - Date of admission

2. **Add New Features**
   - Search functionality
   - Filter by room
   - Export to CSV
   - Email notifications

3. **Improve UI**
   - Add animations
   - Better mobile layout
   - Custom color scheme

## 🚀 Deploy to Production

When ready to deploy:

1. **Read Deployment Guide**
   - See `DEPLOYMENT.md` for detailed instructions

2. **Deploy Backend to Render**
   - Free hosting for backend
   - Automatic deployments from Git

3. **Deploy Frontend to Vercel**
   - Free hosting for frontend
   - Automatic deployments from Git

## 🐛 Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

**Database connection fails:**
- Check Neon DB is active (not suspended)
- Verify connection string is correct
- Ensure `?sslmode=require` is included
- Test connection in Neon SQL Editor

**Build fails:**
- Check Java version: `java -version`
- Clear Maven cache: `mvn clean`
- Delete `target/` folder and rebuild

### Frontend Issues

**Port 3000 already in use:**
```bash
# Find process using port 3000
lsof -i :3000

# Kill the process
kill -9 <PID>
```

**API calls fail:**
- Check backend is running on :8080
- Verify `.env.local` has correct API URL
- Check browser console for errors
- Verify CORS is enabled in backend

**Build fails:**
- Check Node version: `node -v`
- Delete `node_modules/`: `rm -rf node_modules`
- Delete `package-lock.json`
- Run `npm install` again

### Common Errors

**"Email already exists"**
- Each student needs a unique email
- Try a different email address

**"Student not found"**
- Student may have been deleted
- Check the ID is correct

**CORS error in browser**
- Ensure `@CrossOrigin` is in controller
- Restart backend server

## 📖 Documentation

- `README.md` - Project overview
- `SETUP.md` - Quick setup guide
- `DEPLOYMENT.md` - Deployment instructions
- `API_EXAMPLES.md` - API documentation
- `PROJECT_STRUCTURE.md` - Architecture details
- `QUICK_REFERENCE.md` - Command reference
- `DATABASE.sql` - Database schema

## 💡 Tips for Success

1. **Keep Both Servers Running**
   - Backend in one terminal
   - Frontend in another terminal

2. **Use Swagger for API Testing**
   - Faster than using the UI
   - See exact request/response

3. **Check Console for Errors**
   - Backend: Terminal output
   - Frontend: Browser console (F12)

4. **Save Your Work**
   - Commit to Git regularly
   - Push to GitHub for backup

5. **Test Before Deploying**
   - Ensure everything works locally
   - Test all features thoroughly

## 🎯 Learning Resources

- [Spring Boot Tutorial](https://spring.io/guides/gs/spring-boot/)
- [Next.js Tutorial](https://nextjs.org/learn)
- [React Documentation](https://react.dev/)
- [Tailwind CSS Docs](https://tailwindcss.com/docs)
- [PostgreSQL Tutorial](https://www.postgresql.org/docs/)

## 🤝 Need Help?

1. Check the troubleshooting section above
2. Review the documentation files
3. Check browser console for errors
4. Check backend terminal for errors
5. Verify all prerequisites are installed

## ✅ Final Checklist

- [ ] Java 17+ installed and verified
- [ ] Maven installed and verified
- [ ] Node.js 18+ installed and verified
- [ ] Neon database created
- [ ] Backend configured with DB credentials
- [ ] Backend running on :8080
- [ ] Swagger UI accessible
- [ ] Frontend dependencies installed
- [ ] Frontend running on :3000
- [ ] Can add students
- [ ] Can view students
- [ ] Can update fees
- [ ] Can delete students
- [ ] Statistics update correctly
- [ ] Dark mode works
- [ ] Mobile responsive

## 🎊 Congratulations!

You've successfully set up a production-ready full-stack application!

This project demonstrates:
- ✅ Modern frontend development (React, Next.js, TypeScript)
- ✅ Backend API development (Spring Boot, Java)
- ✅ Database design (PostgreSQL)
- ✅ RESTful API design
- ✅ Error handling
- ✅ Responsive UI design
- ✅ Dark mode implementation
- ✅ Cloud database integration

Perfect for your portfolio and resume! 🚀

---

**Ready to deploy?** Check out `DEPLOYMENT.md`!

**Want to customize?** Check out `PROJECT_STRUCTURE.md`!

**Need API reference?** Check out `API_EXAMPLES.md`!
