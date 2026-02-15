# API Examples

Complete API documentation with examples for testing.

## Base URL

- **Local**: `http://localhost:8080/api`
- **Production**: `https://your-backend.onrender.com/api`

## 📋 Endpoints

### 1. Create Student

**POST** `/students`

Create a new student in the hostel.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "roomNumber": "101",
  "feesPaid": false
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "roomNumber": "101",
  "feesPaid": false,
  "createdAt": "2024-01-15T10:30:00"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "roomNumber": "101",
    "feesPaid": false
  }'
```

**Error Response (409 Conflict):**
```json
{
  "status": 409,
  "message": "Student with email john.doe@example.com already exists",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

### 2. Get All Students

**GET** `/students`

Retrieve all students.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "roomNumber": "101",
    "feesPaid": false,
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "roomNumber": "102",
    "feesPaid": true,
    "createdAt": "2024-01-15T11:00:00"
  }
]
```

**cURL Example:**
```bash
curl http://localhost:8080/api/students
```

---

### 3. Get Student by ID

**GET** `/students/{id}`

Retrieve a specific student by ID.

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "roomNumber": "101",
  "feesPaid": false,
  "createdAt": "2024-01-15T10:30:00"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/students/1
```

**Error Response (404 Not Found):**
```json
{
  "status": 404,
  "message": "Student not found with id: 999",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

### 4. Update Student

**PUT** `/students/{id}`

Update an existing student.

**Request Body:**
```json
{
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "roomNumber": "103",
  "feesPaid": true
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "roomNumber": "103",
  "feesPaid": true,
  "createdAt": "2024-01-15T10:30:00"
}
```

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "email": "john.updated@example.com",
    "roomNumber": "103",
    "feesPaid": true
  }'
```

---

### 5. Delete Student

**DELETE** `/students/{id}`

Delete a student.

**Response (200 OK):**
```json
{
  "message": "Student deleted successfully"
}
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

---

### 6. Get Students by Room Number

**GET** `/students/room/{roomNumber}`

Get all students in a specific room.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "roomNumber": "101",
    "feesPaid": false,
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "id": 3,
    "name": "Bob Wilson",
    "email": "bob@example.com",
    "roomNumber": "101",
    "feesPaid": true,
    "createdAt": "2024-01-15T12:00:00"
  }
]
```

**cURL Example:**
```bash
curl http://localhost:8080/api/students/room/101
```

---

### 7. Get Students by Fee Status

**GET** `/students/fees/{status}`

Get students by fee payment status (true/false).

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "roomNumber": "102",
    "feesPaid": true,
    "createdAt": "2024-01-15T11:00:00"
  }
]
```

**cURL Examples:**
```bash
# Get students who paid fees
curl http://localhost:8080/api/students/fees/true

# Get students who haven't paid fees
curl http://localhost:8080/api/students/fees/false
```

---

### 8. Get Statistics

**GET** `/students/stats`

Get student statistics.

**Response (200 OK):**
```json
{
  "totalStudents": 10,
  "unpaidStudents": 3
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/students/stats
```

---

## 🧪 Testing with Postman

### Import Collection

Create a new Postman collection with these requests:

1. **Create Student**
   - Method: POST
   - URL: `{{baseUrl}}/students`
   - Body (raw JSON):
     ```json
     {
       "name": "Test Student",
       "email": "test@example.com",
       "roomNumber": "101",
       "feesPaid": false
     }
     ```

2. **Get All Students**
   - Method: GET
   - URL: `{{baseUrl}}/students`

3. **Get Student by ID**
   - Method: GET
   - URL: `{{baseUrl}}/students/1`

4. **Update Student**
   - Method: PUT
   - URL: `{{baseUrl}}/students/1`
   - Body (raw JSON):
     ```json
     {
       "name": "Updated Name",
       "email": "updated@example.com",
       "roomNumber": "102",
       "feesPaid": true
     }
     ```

5. **Delete Student**
   - Method: DELETE
   - URL: `{{baseUrl}}/students/1`

### Environment Variables

Create a Postman environment:

**Local:**
```json
{
  "baseUrl": "http://localhost:8080/api"
}
```

**Production:**
```json
{
  "baseUrl": "https://your-backend.onrender.com/api"
}
```

---

## 🔍 Validation Rules

### Name
- Required
- Cannot be blank
- Max length: 255 characters

### Email
- Required
- Must be valid email format
- Must be unique
- Max length: 255 characters

### Room Number
- Optional
- Max length: 50 characters

### Fees Paid
- Boolean (true/false)
- Default: false

---

## ❌ Error Responses

### 400 Bad Request (Validation Error)
```json
{
  "status": 400,
  "errors": {
    "name": "Name is required",
    "email": "Email should be valid"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Student not found with id: 999",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 409 Conflict (Duplicate Email)
```json
{
  "status": 409,
  "message": "Student with email john@example.com already exists",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "An unexpected error occurred: Database connection failed",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 🎯 Test Scenarios

### Scenario 1: Complete CRUD Operations

```bash
# 1. Create a student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@test.com","roomNumber":"101","feesPaid":false}'

# 2. Get all students
curl http://localhost:8080/api/students

# 3. Update the student (use ID from step 1)
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Updated","email":"alice@test.com","roomNumber":"102","feesPaid":true}'

# 4. Get student by ID
curl http://localhost:8080/api/students/1

# 5. Delete the student
curl -X DELETE http://localhost:8080/api/students/1
```

### Scenario 2: Room Management

```bash
# Add students to room 101
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Student 1","email":"s1@test.com","roomNumber":"101","feesPaid":false}'

curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Student 2","email":"s2@test.com","roomNumber":"101","feesPaid":true}'

# Get all students in room 101
curl http://localhost:8080/api/students/room/101
```

### Scenario 3: Fee Management

```bash
# Get unpaid students
curl http://localhost:8080/api/students/fees/false

# Get paid students
curl http://localhost:8080/api/students/fees/true

# Get statistics
curl http://localhost:8080/api/students/stats
```

---

## 📊 Sample Data Set

Use this to populate your database for testing:

```bash
# Student 1
curl -X POST http://localhost:8080/api/students -H "Content-Type: application/json" \
  -d '{"name":"Alice Johnson","email":"alice@hostel.com","roomNumber":"101","feesPaid":true}'

# Student 2
curl -X POST http://localhost:8080/api/students -H "Content-Type: application/json" \
  -d '{"name":"Bob Smith","email":"bob@hostel.com","roomNumber":"102","feesPaid":false}'

# Student 3
curl -X POST http://localhost:8080/api/students -H "Content-Type: application/json" \
  -d '{"name":"Charlie Brown","email":"charlie@hostel.com","roomNumber":"101","feesPaid":false}'

# Student 4
curl -X POST http://localhost:8080/api/students -H "Content-Type: application/json" \
  -d '{"name":"Diana Prince","email":"diana@hostel.com","roomNumber":"103","feesPaid":true}'

# Student 5
curl -X POST http://localhost:8080/api/students -H "Content-Type: application/json" \
  -d '{"name":"Eve Adams","email":"eve@hostel.com","roomNumber":"102","feesPaid":false}'
```

---

## 🔗 Swagger UI

Access interactive API documentation:

**Local:** http://localhost:8080/swagger-ui.html

**Production:** https://your-backend.onrender.com/swagger-ui.html

Swagger provides:
- Interactive API testing
- Request/response examples
- Schema documentation
- Try it out functionality

---

Happy Testing! 🚀
