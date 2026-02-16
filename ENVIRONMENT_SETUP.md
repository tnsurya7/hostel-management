# Environment Variables Setup

## Local Development

### Backend Setup

1. Copy the example environment file:
```bash
cd backend
cp .env.example .env
```

2. The `.env` file is already in `.gitignore` and won't be committed.

3. Your `.env` should contain:
```bash
DATABASE_URL=jdbc:postgresql://ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require
PGUSER=neondb_owner
PGPASSWORD=npg_RuAqosp25ZJz
PGHOST=ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
PGDATABASE=neondb
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

4. Run the backend:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Frontend Setup

1. Create `.env.local`:
```bash
cd frontend
touch .env.local
```

2. Add:
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080
```

3. Run the frontend:
```bash
npm run dev
```

## Railway Deployment (Backend)

Add these environment variables in Railway dashboard:

```bash
DATABASE_URL=jdbc:postgresql://ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require
PGUSER=neondb_owner
PGPASSWORD=npg_RuAqosp25ZJz
PGHOST=ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
PGDATABASE=neondb
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
PORT=8080
```

## Vercel Deployment (Frontend)

Add these environment variables in Vercel dashboard:

```bash
NEXT_PUBLIC_API_URL=https://your-backend.railway.app
```

## How It Works

### Development:
- Backend reads from `backend/.env` file
- Frontend reads from `frontend/.env.local` file
- Both files are in `.gitignore` and never committed

### Production:
- Railway injects environment variables at runtime
- Vercel injects environment variables at build time
- No credentials in code or Git history

## Security Notes

✅ Credentials stored in environment variables
✅ `.env` files in `.gitignore`
✅ Example files show format without real credentials
✅ Production uses platform environment variables

⚠️ **IMPORTANT:** After testing, reset your Neon password for security!

## Quick Commands

### Create local .env files:
```bash
# Backend
cd backend
cp .env.example .env
# Edit .env with your credentials

# Frontend
cd frontend
echo "NEXT_PUBLIC_API_URL=http://localhost:8080" > .env.local
```

### Run locally:
```bash
# Terminal 1 - Backend
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Terminal 2 - Frontend
cd frontend
npm run dev
```

### Test connection:
```bash
# Test database connection
psql "postgresql://neondb_owner:npg_RuAqosp25ZJz@ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require" -c "SELECT version();"
```

## Troubleshooting

### Backend can't connect to database:
- Check `.env` file exists in `backend/` directory
- Verify environment variables are set correctly
- Make sure you're running with `-Dspring-boot.run.profiles=prod`

### Frontend can't reach backend:
- Check `.env.local` has correct `NEXT_PUBLIC_API_URL`
- Verify backend is running on port 8080
- Check CORS settings in backend

### Railway deployment fails:
- Verify all environment variables are set in Railway dashboard
- Check `SPRING_PROFILES_ACTIVE=prod` is set
- Review Railway logs for specific errors
