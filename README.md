# Hostel Management System

A full-stack hostel management application built with Next.js 14, Spring Boot, and PostgreSQL (Neon DB).

## 🚀 Features

- Add, view, update, and delete students
- Track fee payment status
- View student statistics
- Search by room number
- Filter unpaid students
- Dark/Light theme support
- Responsive design
- RESTful API with Swagger documentation

## 🛠️ Tech Stack

### Frontend
- Next.js 14 (App Router)
- TypeScript
- Tailwind CSS
- Axios
- Lucide React Icons

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL (Neon DB)
- Lombok
- Swagger/OpenAPI

## 📁 Project Structure

```
hostel-management/
├── backend/
│   ├── src/main/java/com/hostel/
│   │   ├── controller/
│   │   │   └── StudentController.java
│   │   ├── service/
│   │   │   └── StudentService.java
│   │   ├── repository/
│   │   │   └── StudentRepository.java
│   │   ├── model/
│   │   │   └── Student.java
│   │   ├── config/
│   │   │   └── OpenApiConfig.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── DuplicateResourceException.java
│   │   └── HostelManagementApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── app/
    │   │   ├── layout.tsx
    │   │   ├── page.tsx
    │   │   └── globals.css
    │   ├── components/
    │   │   ├── StudentForm.tsx
    │   │   ├── StudentTable.tsx
    │   │   ├── StatsCard.tsx
    │   │   └── ThemeToggle.tsx
    │   ├── lib/
    │   │   ├── axios.ts
    │   │   └── api.ts
    │   └── types/
    │       └── student.ts
    ├── package.json
    ├── tsconfig.json
    ├── tailwind.config.ts
    └── next.config.mjs
```

## 🔧 Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- Maven
- Neon PostgreSQL account

### Backend Setup

1. **Create Neon Database**
   - Go to [Neon Console](https://console.neon.tech/)
   - Create a new project
   - Copy the connection string

2. **Configure Database**
   ```bash
   cd backend
   ```
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://ep-xxxx-xxxx.us-east-2.aws.neon.tech/neondb?sslmode=require
   spring.datasource.username=your_neon_username
   spring.datasource.password=your_neon_password
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   
   Backend will run on `http://localhost:8080`

4. **Access Swagger UI**
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Frontend Setup

1. **Install Dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Configure Environment**
   
   For local development (`.env.local`):
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8080/api
   ```
   
   For production (`.env.production`):
   ```env
   NEXT_PUBLIC_API_URL=https://your-backend-url.onrender.com/api
   ```

3. **Run Development Server**
   ```bash
   npm run dev
   ```
   
   Frontend will run on `http://localhost:3000`

4. **Build for Production**
   ```bash
   npm run build
   npm start
   ```

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/students` | Create new student |
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/api/students/room/{roomNumber}` | Get students by room |
| GET | `/api/students/fees/{status}` | Get students by fee status |
| GET | `/api/students/stats` | Get statistics |

## 📝 Example API Payloads

### Create Student
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "roomNumber": "101",
  "feesPaid": false
}
```

### Update Student
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "roomNumber": "102",
  "feesPaid": true
}
```

### Response
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "roomNumber": "101",
  "feesPaid": false,
  "createdAt": "2024-01-15T10:30:00"
}
```

## 🚀 Deployment

### Deploy Backend to Render

1. **Create Render Account**
   - Go to [Render](https://render.com/)
   - Sign up/Login

2. **Create Web Service**
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Configure:
     - Name: `hostel-backend`
     - Environment: `Java`
     - Build Command: `mvn clean install`
     - Start Command: `java -jar target/hostel-management-1.0.0.jar`

3. **Add Environment Variables**
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://ep-xxxx.neon.tech/neondb?sslmode=require
   SPRING_DATASOURCE_USERNAME=your_username
   SPRING_DATASOURCE_PASSWORD=your_password
   ```

4. **Deploy**
   - Click "Create Web Service"
   - Wait for deployment
   - Copy the service URL

### Deploy Frontend to Vercel

1. **Install Vercel CLI**
   ```bash
   npm install -g vercel
   ```

2. **Deploy**
   ```bash
   cd frontend
   vercel
   ```

3. **Configure Environment Variables**
   - Go to Vercel Dashboard
   - Select your project
   - Settings → Environment Variables
   - Add:
     ```
     NEXT_PUBLIC_API_URL=https://your-backend-url.onrender.com/api
     ```

4. **Redeploy**
   ```bash
   vercel --prod
   ```

## 🗄️ Database Schema

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

## 🎨 Features Showcase

- **Dashboard**: View all students with statistics
- **Add Student**: Form with validation
- **Update Fees**: Toggle fee payment status
- **Delete Student**: Remove student with confirmation
- **Dark Mode**: Toggle between light and dark themes
- **Responsive**: Works on all device sizes
- **Error Handling**: User-friendly error messages
- **Loading States**: Visual feedback during operations

## 📚 Additional Features (Optional Enhancements)

- Room capacity management
- Search functionality
- Pagination for large datasets
- Export to CSV/PDF
- Email notifications
- Student attendance tracking
- Payment history

## 🤝 Contributing

Feel free to fork this project and submit pull requests!

## 📄 License

MIT License
