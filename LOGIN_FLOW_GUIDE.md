# 🔐 Login Flow Guide - Complete Testing

## ✅ System Status
- **Backend**: Running on http://localhost:8080
- **Frontend**: Running on http://localhost:3000
- **Database**: H2 with fresh data loaded

---

## 🎯 How Login Works Now

### 1. Login Page (http://localhost:3000/login)
- User selects type: Admin / Student / Parent
- User enters credentials or clicks quick login button
- System authenticates with backend API
- Backend returns JWT token + user info with roles
- Frontend stores token and redirects based on role

### 2. Role-Based Routing
```
ADMIN role    → /dashboard/admin
STUDENT role  → /dashboard/student
PARENT role   → /dashboard/parent
```

---

## 🔐 Demo Credentials & Expected Behavior

### 👨‍💼 Admin Login
```
Username: admin
Password: admin123
Role: ADMIN
```

**What Happens:**
1. Login successful
2. Redirects to `/dashboard/admin`
3. Shows:
   - Total Students: 15
   - Total Rooms: 27
   - Pending Fees count
   - Active Complaints count
   - Pending Leave Requests count
   - All Features: 20/20
   - Student table with all 15 students

**API Calls Made:**
- `GET /api/students` - All students
- `GET /api/rooms` - All rooms
- `GET /api/fees/payments` - All fee payments
- `GET /api/complaints` - All complaints
- `GET /api/leave-requests` - All leave requests

---

### 👨‍🎓 Student Login
```
Username: student
Password: student123
Role: STUDENT
Email: student@hostel.com
```

**What Happens:**
1. Login successful
2. Redirects to `/dashboard/student`
3. Shows:
   - Student Profile: John Doe
   - Course: Computer Science
   - Attendance Percentage (calculated from records)
   - Pending Fees count (for this student)
   - Leave Requests count (for this student)
   - Complaints count (for this student)
   - Personal Info (email, phone, blood group)
   - Parent Details

**API Calls Made:**
- `GET /api/students` - Find student by email (student@hostel.com)
- `GET /api/fees/payments/student/{id}` - This student's fees
- `GET /api/leave-requests` - Filter by student ID
- `GET /api/complaints` - Filter by student ID
- `GET /api/attendance` - Filter by student ID

**Data Matching:**
- The student user (student@hostel.com) matches the first student record (John Doe)
- All data shown is specific to John Doe

---

### 👨‍👩‍👦 Parent Login
```
Username: parent
Password: parent123
Role: PARENT
Email: parent@hostel.com
```

**What Happens:**
1. Login successful
2. Redirects to `/dashboard/parent`
3. Shows:
   - Child Information: John Doe (matched by parent email)
   - Course & Year
   - Room Number
   - Pending Fees count (for child)
   - Pending Leave Requests (for child)
   - Total Leaves count
   - Pending Leave Requests List
   - Pending Fee Payments Table

**API Calls Made:**
- `GET /api/students` - Find child by parent email (parent@hostel.com)
- `GET /api/leave-requests` - Filter by child's student ID
- `GET /api/fees/payments/student/{id}` - Child's fees

**Data Matching:**
- The parent user (parent@hostel.com) matches John Doe's parent email
- All data shown is specific to John Doe (the child)

---

## 🧪 Step-by-Step Testing

### Test 1: Admin Login Flow
1. Go to http://localhost:3000
2. Should redirect to `/login`
3. Click "Admin Demo" button
4. Credentials auto-fill: admin / admin123
5. Click "Sign In"
6. Should see loading spinner
7. Should redirect to `/dashboard/admin`
8. Should see:
   - "Welcome back, Admin User"
   - Total Students: 15
   - Total Rooms: 27
   - Student table with data
9. Click "Logout"
10. Should redirect back to `/login`

**✅ Success Criteria:**
- No errors in console
- Dashboard loads with correct data
- All statistics show numbers
- Student table shows 15 students

---

### Test 2: Student Login Flow
1. Go to http://localhost:3000/login
2. Click "Student Demo" button
3. Credentials auto-fill: student / student123
4. Click "Sign In"
5. Should redirect to `/dashboard/student`
6. Should see:
   - "Welcome back, John Doe"
   - Profile shows "John Doe"
   - Course: Computer Science
   - Attendance percentage
   - Personal info section
   - Parent details section
7. Click "Logout"
8. Should redirect back to `/login`

**✅ Success Criteria:**
- Shows John Doe's profile
- Attendance percentage calculated
- Personal info displayed
- Parent details shown
- No errors in console

---

### Test 3: Parent Login Flow
1. Go to http://localhost:3000/login
2. Click "Parent Demo" button
3. Credentials auto-fill: parent / parent123
4. Click "Sign In"
5. Should redirect to `/dashboard/parent`
6. Should see:
   - "Welcome, Parent User"
   - Child Information: John Doe
   - Course & Year displayed
   - Pending fees count
   - Leave requests list
   - Fee payments table
7. Click "Logout"
8. Should redirect back to `/login`

**✅ Success Criteria:**
- Shows John Doe as child
- Child information complete
- Fees and leaves displayed
- No errors in console

---

## 🔍 Debugging Tips

### Check Browser Console (F12)
Look for these logs:
```
Login response: {token: "...", id: 2, username: "student", email: "student@hostel.com", roles: ["STUDENT"]}
User roles: ["STUDENT"]
Redirecting to student dashboard
```

### Check Network Tab (F12 → Network)
1. **Login Request:**
   - URL: `http://localhost:8080/api/auth/login`
   - Method: POST
   - Status: 200
   - Response: JWT token + user info

2. **Dashboard API Calls:**
   - Multiple GET requests to `/api/students`, `/api/fees/payments`, etc.
   - All should return 200 status
   - Check response data

### Check LocalStorage (F12 → Application → Local Storage)
Should contain:
```
token: "eyJhbGciOiJIUzI1NiJ9..."
userId: "2"
user: {"id":2,"username":"student","email":"student@hostel.com","roles":["STUDENT"]}
```

---

## 🐛 Common Issues & Solutions

### Issue 1: Login doesn't redirect
**Symptoms:** Click Sign In, nothing happens
**Solution:**
1. Check browser console for errors
2. Verify backend is running: `curl http://localhost:8080/api/auth/login`
3. Clear localStorage and try again
4. Check Network tab for failed requests

### Issue 2: Dashboard shows no data
**Symptoms:** Dashboard loads but shows 0 for all stats
**Solution:**
1. Check if backend data initialized (look for "Data initialization completed" in backend logs)
2. Verify API calls in Network tab
3. Check if JWT token is in Authorization header
4. Try logging out and back in

### Issue 3: Wrong dashboard loads
**Symptoms:** Student login goes to admin dashboard
**Solution:**
1. Check roles in login response (console log)
2. Verify user has correct role in database
3. Clear localStorage completely
4. Login again

### Issue 4: 401 Unauthorized errors
**Symptoms:** API calls fail with 401
**Solution:**
1. Check if token is stored in localStorage
2. Verify token is in Authorization header
3. Token might be expired - logout and login again
4. Check backend logs for authentication errors

---

## 📊 Data Relationships

### User → Student Mapping
```
User: student@hostel.com (STUDENT role)
  ↓
Student: John Doe (email: student@hostel.com)
  ↓
Data: Fees, Leaves, Complaints, Attendance for John Doe
```

### User → Parent → Child Mapping
```
User: parent@hostel.com (PARENT role)
  ↓
Student: John Doe (parentEmail: parent@hostel.com)
  ↓
Data: Child's Fees, Leaves, Attendance
```

### Admin Access
```
User: admin@hostel.com (ADMIN role)
  ↓
Access: ALL students, ALL rooms, ALL data
```

---

## 🎯 API Endpoints Used

### Authentication
- `POST /api/auth/login` - Login and get JWT token

### Admin Dashboard
- `GET /api/students` - All students
- `GET /api/rooms` - All rooms
- `GET /api/fees/payments` - All payments
- `GET /api/complaints` - All complaints
- `GET /api/leave-requests` - All leave requests

### Student Dashboard
- `GET /api/students` - Find by email
- `GET /api/fees/payments/student/{id}` - Student's fees
- `GET /api/leave-requests` - Filter by student
- `GET /api/complaints` - Filter by student
- `GET /api/attendance` - Filter by student

### Parent Dashboard
- `GET /api/students` - Find child by parent email
- `GET /api/leave-requests` - Filter by child
- `GET /api/fees/payments/student/{id}` - Child's fees

---

## ✅ Expected Results Summary

| User Type | Login | Dashboard | Data Shown |
|-----------|-------|-----------|------------|
| Admin | ✅ admin/admin123 | /dashboard/admin | All 15 students, 27 rooms, all data |
| Student | ✅ student/student123 | /dashboard/student | John Doe's profile & data only |
| Parent | ✅ parent/parent123 | /dashboard/parent | John Doe (child) data only |

---

## 🚀 Quick Test Commands

### Test Backend is Running
```bash
curl http://localhost:8080/api/students
# Should return 401 (needs auth) - means backend is up
```

### Test Login API
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
# Should return JWT token
```

### Test with Token
```bash
TOKEN="your-jwt-token-here"
curl http://localhost:8080/api/students \
  -H "Authorization: Bearer $TOKEN"
# Should return 15 students
```

---

## 📞 Support

If something doesn't work:
1. Check backend logs (terminal where backend is running)
2. Check frontend console (F12 in browser)
3. Check Network tab for failed requests
4. Verify both servers are running
5. Try clearing browser cache and localStorage

---

**Last Updated**: February 15, 2026
**Status**: ✅ All login flows working correctly
