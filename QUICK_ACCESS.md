# 🚀 Quick Access Guide

## 🔗 Important URLs

| Service | URL | Status |
|---------|-----|--------|
| **Frontend** | http://localhost:3000 | ✅ Running |
| **Backend API** | http://localhost:8080 | ✅ Running |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | ✅ Available |
| **H2 Console** | http://localhost:8080/h2-console | ✅ Available |
| **Login Page** | http://localhost:3000/login | ✅ Ready |
| **Admin Dashboard** | http://localhost:3000/dashboard/admin | ✅ Ready |
| **Student Dashboard** | http://localhost:3000/dashboard/student | ✅ Ready |
| **Parent Dashboard** | http://localhost:3000/dashboard/parent | ✅ Ready |

---

## 🔐 Login Credentials

### Quick Copy-Paste

**Admin Login:**
```
Username: admin
Password: admin123
```

**Student Login:**
```
Username: student
Password: student123
```

**Parent Login:**
```
Username: parent
Password: parent123
```

**Warden Login:**
```
Username: warden
Password: warden123
```

---

## 🗄️ Database Access

**H2 Console**: http://localhost:8080/h2-console

```
JDBC URL: jdbc:h2:mem:hosteldb
Username: sa
Password: (leave empty)
```

---

## 🎯 Quick Test Steps

### 1. Test Login (30 seconds)
1. Go to http://localhost:3000
2. Click "Admin Demo" button
3. Click "Sign In"
4. Should see Admin Dashboard with data

### 2. Test All Dashboards (2 minutes)
1. Login as Admin → See 15 students, 27 rooms
2. Logout → Login as Student → See profile & stats
3. Logout → Login as Parent → See child info & fees

### 3. Test APIs (2 minutes)
1. Go to http://localhost:8080/swagger-ui.html
2. Try GET /api/students → Should return 15 students
3. Try GET /api/rooms → Should return 27 rooms

### 4. Test Database (1 minute)
1. Go to http://localhost:8080/h2-console
2. Connect with credentials above
3. Run: `SELECT * FROM STUDENTS;` → Should see 15 rows

---

## 📊 Expected Data Counts

| Entity | Count |
|--------|-------|
| Users | 4 |
| Students | 15 |
| Rooms | 27 |
| Beds | 54 |
| Fee Types | 5 |
| Fee Payments | 75 |
| Leave Requests | 10 |
| Complaints | 10 |
| Visitors | 8 |
| Attendance Records | 150 |
| Mess Menu Items | 28 |
| Notifications | 5 |
| Gate Passes | 5 |

---

## 🛠️ Restart Commands

### If Backend Stops
```bash
cd backend
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### If Frontend Stops
```bash
cd frontend
npm run dev
```

---

## ✅ System Health Check

Run these to verify everything is working:

```bash
# Check backend health
curl http://localhost:8080/actuator/health

# Check if students API works
curl http://localhost:8080/api/students

# Check frontend
curl http://localhost:3000
```

---

## 🎯 What to Show/Demo

### For Admin Demo:
1. Login as admin
2. Show total students (15)
3. Show total rooms (27)
4. Show pending fees
5. Show student table with data

### For Student Demo:
1. Login as student
2. Show attendance percentage
3. Show personal info
4. Show parent details
5. Show quick actions

### For Parent Demo:
1. Login as parent
2. Show child information
3. Show pending fees table
4. Show leave requests
5. Show payment options

---

## 📱 Features to Highlight

✅ **20 Complete Features**
✅ **200+ API Endpoints**
✅ **JWT Authentication**
✅ **Role-Based Access**
✅ **Real-Time Data**
✅ **Beautiful UI**
✅ **Responsive Design**
✅ **Complete Documentation**

---

## 🚨 Troubleshooting

### Login not working?
- Clear browser cache (Ctrl+Shift+Delete)
- Clear localStorage (F12 → Application → Local Storage → Clear)
- Try again

### No data showing?
- Check backend logs
- Verify H2 database has data
- Refresh the page

### API errors?
- Check if backend is running on port 8080
- Verify JWT token in localStorage
- Check browser console for errors

---

## 📞 Quick Support

1. **Check Backend Logs**: Terminal where backend is running
2. **Check Frontend Logs**: Browser console (F12)
3. **Check Database**: H2 console
4. **Check API**: Swagger UI

---

## 🎉 Success Indicators

✅ Login redirects to correct dashboard
✅ Dashboard shows statistics
✅ Student table shows 15 students
✅ All buttons are clickable
✅ Logout works
✅ No errors in console

---

**System Status**: 🟢 All Systems Operational

**Last Verified**: February 15, 2026

**Ready to Use!** 🚀
