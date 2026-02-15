# Quick Reference Guide

Fast lookup for common tasks and commands.

## 🚀 Quick Start Commands

### Backend
```bash
cd backend
mvn clean install          # Build project
mvn spring-boot:run        # Run application
mvn test                   # Run tests
```

### Frontend
```bash
cd frontend
npm install                # Install dependencies
npm run dev                # Development server
npm run build              # Production build
npm start                  # Start production server
```

## 🔗 Important URLs

### Local Development
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Base: http://localhost:8080/api

### Production (Update with your URLs)
- Frontend: https://your-app.vercel.app
- Backend: https://your-backend.onrender.com
- Swagger: https://your-backend.onrender.com/swagger-ui.html

## 📡 API Endpoints Quick Reference

```
POST   /api/students              Create student
GET    /api/students              Get all students
GET    /api/students/{id}         Get student by ID
PUT    /api/students/{id}         Update student
DELETE /api/students/{id}         Delete student
GET    /api/students/room/{num}   Get by room
GET    /api/students/fees/{bool}  Get by fee status
GET    /api/students/stats        Get statistics
```

## 🧪 Quick Test Commands

### Test with cURL

**Create Student:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@test.com","roomNumber":"101","feesPaid":false}'
```

**Get All Students:**
```bash
curl http://localhost:8080/api/students
```

**Get Statistics:**
```bash
curl http://localhost:8080/api/students/stats
```

**Delete Student:**
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

## 🗄️ Database Quick Commands

### Connect to Neon DB
```bash
psql "postgresql://username:password@ep-xxxx.neon.tech/neondb?sslmode=require"
```

### Common SQL Queries
```sql
-- View all students
SELECT * FROM students;

-- Count students
SELECT COUNT(*) FROM students;

-- Get unpaid students
SELECT * FROM students WHERE fees_paid = false;

-- Delete all students
DELETE FROM students;
```

## 🔧 Configuration Files

### Backend Config
**File:** `backend/src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://YOUR_HOST/neondb?sslmode=require
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Frontend Config
**File:** `frontend/.env.local`
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

**File:** `frontend/.env.production`
```env
NEXT_PUBLIC_API_URL=https://your-backend.onrender.com/api
```

## 🐛 Troubleshooting Quick Fixes

### Backend won't start
```bash
# Check Java version
java -version

# Clean and rebuild
mvn clean install -U

# Check port 8080
lsof -i :8080
kill -9 <PID>
```

### Frontend won't start
```bash
# Check Node version
node -v

# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install

# Check port 3000
lsof -i :3000
kill -9 <PID>
```

### Database connection fails
```bash
# Test connection
psql "postgresql://user:pass@host/db?sslmode=require"

# Check Neon DB status
# Visit: https://console.neon.tech/
```

### CORS errors
- Check `@CrossOrigin` in controller
- Verify API URL in frontend
- Check browser console for details

## 📦 Dependencies

### Backend (pom.xml)
```xml
spring-boot-starter-web
spring-boot-starter-data-jpa
postgresql
lombok
spring-boot-starter-validation
springdoc-openapi-starter-webmvc-ui
```

### Frontend (package.json)
```json
next: 14.2.0
react: 18.3.0
axios: 1.6.7
tailwindcss: 3.4.0
lucide-react: 0.344.0
```

## 🚀 Deployment Quick Steps

### Deploy Backend to Render
1. Push to GitHub
2. Create Web Service on Render
3. Connect repository
4. Add environment variables
5. Deploy

### Deploy Frontend to Vercel
1. Push to GitHub
2. Import project on Vercel
3. Add environment variables
4. Deploy

## 📊 Sample Data

### Quick Insert
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "roomNumber": "101",
  "feesPaid": false
}
```

### Bulk Insert (SQL)
```sql
INSERT INTO students (name, email, room_number, fees_paid) VALUES
('Alice', 'alice@test.com', '101', true),
('Bob', 'bob@test.com', '102', false),
('Charlie', 'charlie@test.com', '103', true);
```

## 🎨 Common UI Tasks

### Toggle Dark Mode
- Click sun/moon icon in header
- Persists to localStorage

### Add Student
1. Click "Add New Student"
2. Fill form
3. Click "Add Student"

### Update Fees
- Click on "Paid" or "Unpaid" badge in table

### Delete Student
- Click trash icon
- Confirm deletion

## 🔐 Environment Variables

### Required for Backend
```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

### Required for Frontend
```
NEXT_PUBLIC_API_URL
```

## 📝 Git Commands

```bash
# Initialize repository
git init
git add .
git commit -m "Initial commit"

# Push to GitHub
git remote add origin https://github.com/username/repo.git
git branch -M main
git push -u origin main

# Update deployment
git add .
git commit -m "Update feature"
git push
```

## 🔍 Logs and Debugging

### Backend Logs
```bash
# View logs in terminal
mvn spring-boot:run

# Check Render logs
# Dashboard → Service → Logs
```

### Frontend Logs
```bash
# Development logs
npm run dev

# Check Vercel logs
# Dashboard → Project → Logs
```

### Database Logs
```bash
# Enable SQL logging
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 🎯 Testing Checklist

- [ ] Backend starts on :8080
- [ ] Frontend starts on :3000
- [ ] Can create student
- [ ] Can view students
- [ ] Can update fees
- [ ] Can delete student
- [ ] Statistics update
- [ ] Dark mode works
- [ ] Mobile responsive
- [ ] API errors handled

## 📞 Support Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Next.js Docs](https://nextjs.org/docs)
- [Neon Docs](https://neon.tech/docs)
- [Tailwind Docs](https://tailwindcss.com/docs)
- [Axios Docs](https://axios-http.com/docs)

## 💡 Pro Tips

1. Use Swagger UI for API testing
2. Check browser console for frontend errors
3. Use Neon SQL Editor for database queries
4. Enable dark mode for better development experience
5. Use environment variables for sensitive data
6. Test locally before deploying
7. Monitor Render/Vercel logs after deployment
8. Keep dependencies updated
9. Use Git for version control
10. Document your changes

---

Keep this guide handy for quick reference! 🚀
