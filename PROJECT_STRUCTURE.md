# Project Structure Overview

Complete breakdown of the Hostel Management System architecture.

## 📂 Directory Structure

```
hostel-management/
│
├── backend/                          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hostel/
│   │   │   │   ├── controller/
│   │   │   │   │   └── StudentController.java      # REST API endpoints
│   │   │   │   ├── service/
│   │   │   │   │   └── StudentService.java         # Business logic
│   │   │   │   ├── repository/
│   │   │   │   │   └── StudentRepository.java      # Database operations
│   │   │   │   ├── model/
│   │   │   │   │   └── Student.java                # Entity model
│   │   │   │   ├── config/
│   │   │   │   │   └── OpenApiConfig.java          # Swagger configuration
│   │   │   │   ├── exception/
│   │   │   │   │   ├── GlobalExceptionHandler.java # Error handling
│   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   └── DuplicateResourceException.java
│   │   │   │   └── HostelManagementApplication.java # Main application
│   │   │   └── resources/
│   │   │       └── application.properties           # Configuration
│   │   └── test/                                    # Test files
│   ├── pom.xml                                      # Maven dependencies
│   └── .gitignore
│
├── frontend/                         # Next.js Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── layout.tsx                          # Root layout
│   │   │   ├── page.tsx                            # Main dashboard page
│   │   │   └── globals.css                         # Global styles
│   │   ├── components/
│   │   │   ├── StudentForm.tsx                     # Add/Edit student form
│   │   │   ├── StudentTable.tsx                    # Students data table
│   │   │   ├── StatsCard.tsx                       # Statistics card
│   │   │   └── ThemeToggle.tsx                     # Dark/Light mode toggle
│   │   ├── lib/
│   │   │   ├── axios.ts                            # Axios instance
│   │   │   └── api.ts                              # API functions
│   │   └── types/
│   │       └── student.ts                          # TypeScript types
│   ├── public/                                      # Static assets
│   ├── package.json                                 # Dependencies
│   ├── tsconfig.json                                # TypeScript config
│   ├── tailwind.config.ts                           # Tailwind config
│   ├── next.config.mjs                              # Next.js config
│   ├── postcss.config.mjs                           # PostCSS config
│   ├── .env.local                                   # Local environment
│   ├── .env.production                              # Production environment
│   └── .gitignore
│
├── README.md                         # Main documentation
├── SETUP.md                          # Quick setup guide
├── DEPLOYMENT.md                     # Deployment instructions
├── API_EXAMPLES.md                   # API documentation
├── PROJECT_STRUCTURE.md              # This file
└── .gitignore                        # Git ignore rules
```

## 🏗️ Architecture Overview

### Backend Architecture (Spring Boot)

```
┌─────────────────────────────────────────────────────────┐
│                    Client (Frontend)                     │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                  Controller Layer                        │
│  - StudentController.java                               │
│  - Handles HTTP requests                                │
│  - Validates input                                       │
│  - Returns responses                                     │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                   Service Layer                          │
│  - StudentService.java                                   │
│  - Business logic                                        │
│  - Transaction management                                │
│  - Data validation                                       │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                 Repository Layer                         │
│  - StudentRepository.java                                │
│  - Database queries                                      │
│  - JPA operations                                        │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                  Database (Neon PostgreSQL)              │
│  - students table                                        │
└─────────────────────────────────────────────────────────┘
```

### Frontend Architecture (Next.js)

```
┌─────────────────────────────────────────────────────────┐
│                    User Interface                        │
│  - page.tsx (Main Dashboard)                            │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                   Components Layer                       │
│  - StudentForm.tsx                                       │
│  - StudentTable.tsx                                      │
│  - StatsCard.tsx                                         │
│  - ThemeToggle.tsx                                       │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                    API Layer                             │
│  - api.ts (API functions)                               │
│  - axios.ts (HTTP client)                               │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                Backend REST API                          │
└─────────────────────────────────────────────────────────┘
```

## 📦 Component Breakdown

### Backend Components

#### 1. StudentController.java
**Purpose:** Handle HTTP requests and responses

**Endpoints:**
- `POST /api/students` - Create student
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student
- `GET /api/students/room/{roomNumber}` - Get by room
- `GET /api/students/fees/{status}` - Get by fee status
- `GET /api/students/stats` - Get statistics

**Key Features:**
- CORS enabled
- Request validation
- Swagger documentation
- ResponseEntity for proper HTTP responses

#### 2. StudentService.java
**Purpose:** Business logic and transaction management

**Methods:**
- `createStudent()` - Validate and create
- `getAllStudents()` - Retrieve all
- `getStudentById()` - Find by ID
- `updateStudent()` - Update with validation
- `deleteStudent()` - Remove student
- `getStudentsByRoom()` - Filter by room
- `getStudentsByFeeStatus()` - Filter by fees
- `getTotalStudentsCount()` - Count total
- `getUnpaidStudentsCount()` - Count unpaid

**Key Features:**
- Email uniqueness validation
- Transaction management
- Exception handling

#### 3. StudentRepository.java
**Purpose:** Database operations

**Methods:**
- `findByEmail()` - Find by email
- `findByRoomNumber()` - Find by room
- `findByFeesPaid()` - Find by fee status
- `countByFeesPaid()` - Count by fee status
- Plus all JpaRepository methods

#### 4. Student.java
**Purpose:** Entity model

**Fields:**
- `id` - Primary key (auto-generated)
- `name` - Student name (required)
- `email` - Email (required, unique)
- `roomNumber` - Room number (optional)
- `feesPaid` - Fee status (boolean)
- `createdAt` - Creation timestamp (auto)

**Annotations:**
- `@Entity` - JPA entity
- `@Table` - Table mapping
- `@Id` - Primary key
- `@GeneratedValue` - Auto-increment
- `@NotBlank` - Validation
- `@Email` - Email validation
- `@CreationTimestamp` - Auto timestamp

#### 5. GlobalExceptionHandler.java
**Purpose:** Centralized error handling

**Handles:**
- `ResourceNotFoundException` - 404 errors
- `DuplicateResourceException` - 409 conflicts
- `MethodArgumentNotValidException` - 400 validation
- `Exception` - 500 server errors

### Frontend Components

#### 1. page.tsx (Main Dashboard)
**Purpose:** Main application page

**Features:**
- Student list display
- Add student form toggle
- Statistics cards
- Error handling
- Loading states
- Theme toggle

**State Management:**
- `students` - List of students
- `stats` - Statistics data
- `isLoading` - Loading state
- `isSubmitting` - Form submission state
- `showForm` - Form visibility
- `error` - Error messages

#### 2. StudentForm.tsx
**Purpose:** Add/Edit student form

**Fields:**
- Name input (required)
- Email input (required)
- Room number input (optional)
- Fees paid checkbox

**Features:**
- Form validation
- Loading state
- Error handling
- Responsive design

#### 3. StudentTable.tsx
**Purpose:** Display students in table

**Features:**
- Sortable columns
- Delete button
- Toggle fees status
- Loading skeleton
- Empty state
- Responsive design

**Columns:**
- ID
- Name
- Email
- Room Number
- Fees Status (clickable)
- Actions (delete)

#### 4. StatsCard.tsx
**Purpose:** Display statistics

**Props:**
- `title` - Card title
- `value` - Numeric value
- `icon` - Icon component
- `color` - Background color

#### 5. ThemeToggle.tsx
**Purpose:** Dark/Light mode toggle

**Features:**
- Persists to localStorage
- System preference detection
- Smooth transitions
- Icon toggle (Sun/Moon)

#### 6. api.ts
**Purpose:** API function definitions

**Functions:**
- `getAllStudents()`
- `getStudentById(id)`
- `createStudent(student)`
- `updateStudent(id, student)`
- `deleteStudent(id)`
- `getStudentsByRoom(roomNumber)`
- `getStudentsByFeeStatus(status)`
- `getStats()`

#### 7. axios.ts
**Purpose:** HTTP client configuration

**Features:**
- Base URL configuration
- Request interceptor
- Response interceptor
- Error handling
- Environment-based URL

## 🔄 Data Flow

### Create Student Flow

```
1. User fills form in StudentForm.tsx
   ↓
2. Form submits to handleAddStudent() in page.tsx
   ↓
3. Calls studentApi.createStudent() in api.ts
   ↓
4. Axios sends POST request to backend
   ↓
5. StudentController receives request
   ↓
6. Validates input with @Valid
   ↓
7. Calls StudentService.createStudent()
   ↓
8. Service checks email uniqueness
   ↓
9. StudentRepository.save() persists to DB
   ↓
10. Response flows back through layers
   ↓
11. Frontend updates state and UI
   ↓
12. Success message or error displayed
```

### Get Students Flow

```
1. Component mounts (useEffect in page.tsx)
   ↓
2. Calls fetchStudents()
   ↓
3. studentApi.getAllStudents() in api.ts
   ↓
4. GET request to /api/students
   ↓
5. StudentController.getAllStudents()
   ↓
6. StudentService.getAllStudents()
   ↓
7. StudentRepository.findAll()
   ↓
8. Database query executed
   ↓
9. Results returned through layers
   ↓
10. Frontend updates students state
   ↓
11. StudentTable renders data
```

## 🎨 Styling Architecture

### Tailwind CSS Classes

**Layout:**
- `max-w-7xl mx-auto` - Container
- `px-4 sm:px-6 lg:px-8` - Responsive padding
- `grid grid-cols-1 md:grid-cols-2` - Responsive grid

**Components:**
- `bg-white dark:bg-gray-800` - Theme support
- `rounded-lg shadow-md` - Card styling
- `hover:bg-gray-50` - Interactive states

**Forms:**
- `border border-gray-300` - Input borders
- `focus:ring-2 focus:ring-blue-500` - Focus states
- `px-3 py-2` - Input padding

**Buttons:**
- `bg-blue-600 hover:bg-blue-700` - Primary button
- `text-white font-medium` - Button text
- `disabled:opacity-50` - Disabled state

## 🔐 Security Features

### Backend
- Input validation with Bean Validation
- SQL injection prevention (JPA)
- CORS configuration
- Exception handling
- Unique email constraint

### Frontend
- Environment variables for API URL
- Input sanitization
- Error message handling
- HTTPS in production

## 📊 Database Schema

```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    room_number VARCHAR(50),
    fees_paid BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_email ON students(email);
CREATE INDEX idx_room_number ON students(room_number);
CREATE INDEX idx_fees_paid ON students(fees_paid);
```

## 🚀 Build Process

### Backend Build
```bash
mvn clean install
# Compiles Java code
# Runs tests
# Packages as JAR
# Output: target/hostel-management-1.0.0.jar
```

### Frontend Build
```bash
npm run build
# Compiles TypeScript
# Bundles with webpack
# Optimizes assets
# Output: .next/ directory
```

## 📈 Performance Considerations

### Backend
- Connection pooling (HikariCP)
- JPA query optimization
- Lazy loading where appropriate
- Proper indexing

### Frontend
- Server-side rendering (Next.js)
- Code splitting
- Image optimization
- CSS purging (Tailwind)

## 🧪 Testing Strategy

### Backend Testing
- Unit tests for services
- Integration tests for repositories
- API tests for controllers

### Frontend Testing
- Component tests
- Integration tests
- E2E tests

## 📝 Configuration Files

### Backend
- `pom.xml` - Maven dependencies
- `application.properties` - App configuration

### Frontend
- `package.json` - NPM dependencies
- `tsconfig.json` - TypeScript config
- `tailwind.config.ts` - Tailwind config
- `next.config.mjs` - Next.js config

## 🔗 External Dependencies

### Backend
- Spring Boot 3.2.0
- PostgreSQL Driver
- Lombok
- SpringDoc OpenAPI

### Frontend
- Next.js 14.2.0
- React 18.3.0
- Axios 1.6.7
- Tailwind CSS 3.4.0
- Lucide React 0.344.0

---

This structure provides a clean, maintainable, and scalable architecture for the Hostel Management System.
