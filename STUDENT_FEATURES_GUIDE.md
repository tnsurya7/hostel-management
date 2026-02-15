# 📚 Student Features Guide - Quick Actions

## 🎯 New Working Pages Added

All Quick Action buttons in the Student Dashboard now lead to fully functional pages:

1. ✅ **Apply for Leave** - Submit leave requests
2. ✅ **Request Gate Pass** - Request gate passes with QR codes
3. ✅ **File Complaint** - Submit complaints with categories
4. ✅ **Pay Fees** - View and pay pending fees

---

## 🚀 How to Use Each Feature

### 1. Apply for Leave

**Path**: `/student/leave-request`

**Steps**:
1. Login as student (student / student123)
2. Click "Apply for Leave" button
3. Fill in the form:
   - **Reason**: Why you need leave
   - **From Date**: Start date
   - **To Date**: End date
   - **Parent Consent**: Check if you have consent
4. Click "Submit Leave Request"
5. Request sent for approval

**Form Fields**:
- Student Name (auto-filled, disabled)
- Reason for Leave (required, textarea)
- From Date (required, date picker)
- To Date (required, date picker)
- Parent Consent (checkbox)

**API Endpoint**: `POST /api/leave-requests`

**Status**: Submitted as "PENDING" for admin approval

---

### 2. Request Gate Pass

**Path**: `/student/gate-pass`

**Steps**:
1. Login as student
2. Click "Request Gate Pass" button
3. Select pass type:
   - Day Pass
   - Weekend Pass
   - Emergency Pass
4. Enter purpose
5. Select from/to date & time
6. Click "Submit Gate Pass Request"
7. QR code will be generated upon approval

**Form Fields**:
- Student Name (auto-filled)
- Pass Type (dropdown: Day/Weekend/Emergency)
- Purpose (required, textarea)
- From Date & Time (datetime picker)
- To Date & Time (datetime picker)

**API Endpoint**: `POST /api/gate-passes`

**Features**:
- Auto-generates pass number (GP-timestamp)
- Auto-generates QR code (QR-timestamp)
- Status: PENDING until approved

---

### 3. File Complaint

**Path**: `/student/complaint`

**Steps**:
1. Login as student
2. Click "File Complaint" button
3. Enter complaint title
4. Select category:
   - Room
   - Mess
   - Maintenance
   - Security
   - Cleanliness
   - Other
5. Select priority (Low/Medium/High)
6. Describe the issue
7. Click "Submit Complaint"

**Form Fields**:
- Student Name (auto-filled)
- Complaint Title (required)
- Category (dropdown, required)
- Priority (dropdown, required)
- Description (required, textarea)

**API Endpoint**: `POST /api/complaints`

**Status**: Created as "OPEN" for admin review

---

### 4. Pay Fees

**Path**: `/student/pay-fees`

**Steps**:
1. Login as student
2. Click "Pay Fees" button
3. View all pending fees in table
4. Select payment method:
   - UPI
   - Credit Card
   - Debit Card
   - Net Banking
5. Click "Pay Now" for any fee
6. Payment processed (simulated)
7. Receipt generated automatically

**Features**:
- Shows all pending fees
- Displays fee type, amount, due date, semester
- Payment method selection
- Real-time payment processing
- Auto-generates transaction ID
- Auto-generates receipt number

**API Endpoint**: `PUT /api/fees/payments/{id}`

**Payment Details**:
- Transaction ID: TXN + timestamp
- Receipt Number: RCP- + timestamp
- Status updated to "PAID"
- Payment date recorded

---

## 🎨 UI Features

### Common Elements

**All pages have**:
- Header with page title
- "Back to Dashboard" button
- Student name auto-filled
- Form validation
- Loading states
- Success/error messages
- Cancel button
- Submit button with loading state

### Design
- Clean, modern interface
- Responsive layout (mobile-friendly)
- Color-coded buttons
- Info boxes with notes
- Proper spacing and typography

---

## 📊 Data Flow

### Leave Request Flow
```
Student fills form
  ↓
POST /api/leave-requests
  ↓
Status: PENDING
  ↓
Admin reviews
  ↓
Status: APPROVED/REJECTED
```

### Gate Pass Flow
```
Student requests pass
  ↓
POST /api/gate-passes
  ↓
Pass number & QR code generated
  ↓
Status: PENDING
  ↓
Admin approves
  ↓
Status: APPROVED
  ↓
Student shows QR at gate
```

### Complaint Flow
```
Student files complaint
  ↓
POST /api/complaints
  ↓
Status: OPEN
  ↓
Admin assigns/reviews
  ↓
Status: IN_PROGRESS
  ↓
Issue resolved
  ↓
Status: RESOLVED
```

### Fee Payment Flow
```
Student views pending fees
  ↓
Selects payment method
  ↓
Clicks "Pay Now"
  ↓
PUT /api/fees/payments/{id}
  ↓
Status: PAID
  ↓
Receipt generated
```

---

## 🧪 Testing Guide

### Test Leave Request
1. Login as student
2. Go to Apply for Leave
3. Fill form:
   ```
   Reason: Family function
   From: Tomorrow's date
   To: Day after tomorrow
   Parent Consent: ✓
   ```
4. Submit
5. Check admin dashboard for new leave request

### Test Gate Pass
1. Login as student
2. Go to Request Gate Pass
3. Fill form:
   ```
   Pass Type: Day Pass
   Purpose: Going home
   From: Today 6 PM
   To: Today 11 PM
   ```
4. Submit
5. Check admin dashboard for new gate pass

### Test Complaint
1. Login as student
2. Go to File Complaint
3. Fill form:
   ```
   Title: AC not working
   Category: Room
   Priority: High
   Description: The AC in my room has stopped working...
   ```
4. Submit
5. Check admin dashboard for new complaint

### Test Pay Fees
1. Login as student
2. Go to Pay Fees
3. Should see pending fees (if any)
4. Select payment method: UPI
5. Click "Pay Now" on any fee
6. Wait for processing (2 seconds)
7. Fee status updated to PAID

---

## 🔧 Technical Details

### Authentication
- All pages check for JWT token
- Redirect to login if not authenticated
- Token sent in Authorization header

### Student Data Fetching
- Fetches all students
- Finds student by email match
- Uses student ID for API calls

### Form Validation
- Required fields marked with *
- HTML5 validation
- Custom error messages
- Disabled submit during loading

### API Integration
- Axios for HTTP requests
- Bearer token authentication
- Error handling with try-catch
- Success/error alerts

---

## 📱 Responsive Design

All pages work on:
- ✅ Desktop (1920px+)
- ✅ Laptop (1366px)
- ✅ Tablet (768px)
- ✅ Mobile (375px)

Features:
- Responsive grid layouts
- Mobile-friendly forms
- Touch-friendly buttons
- Readable text sizes

---

## 🎯 Quick Reference

| Feature | Path | API Endpoint | Status |
|---------|------|--------------|--------|
| Leave Request | /student/leave-request | POST /api/leave-requests | PENDING |
| Gate Pass | /student/gate-pass | POST /api/gate-passes | PENDING |
| Complaint | /student/complaint | POST /api/complaints | OPEN |
| Pay Fees | /student/pay-fees | PUT /api/fees/payments/{id} | PAID |

---

## 💡 Tips for Students

### Leave Requests
- ✅ Apply at least 2 days in advance
- ✅ Get parent consent before applying
- ✅ Provide clear reason
- ✅ Check status in dashboard

### Gate Passes
- ✅ Request early (not last minute)
- ✅ Be specific about purpose
- ✅ Return on time to avoid penalties
- ✅ Show QR code at gate

### Complaints
- ✅ Be specific and detailed
- ✅ Choose correct category
- ✅ Set appropriate priority
- ✅ Follow up if needed

### Fee Payments
- ✅ Pay before due date
- ✅ Keep receipt for records
- ✅ Check payment status
- ✅ Contact admin if issues

---

## 🐛 Troubleshooting

### Issue: Form not submitting
**Solution**: Check all required fields are filled

### Issue: "Failed to fetch" error
**Solution**: Ensure backend is running on port 8080

### Issue: Not redirecting after submit
**Solution**: Check browser console for errors

### Issue: Student data not loading
**Solution**: Verify you're logged in with correct credentials

---

## 📞 Support

If you encounter issues:
1. Check browser console (F12)
2. Verify backend is running
3. Check JWT token in localStorage
4. Try logging out and back in

---

**Last Updated**: February 15, 2026
**Status**: ✅ All Features Working
**Pages Created**: 4 new functional pages
