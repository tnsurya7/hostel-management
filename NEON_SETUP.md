# Neon Database Setup Guide

## Step 1: Get Your Neon Connection Details

1. Go to [Neon Console](https://console.neon.tech)
2. Select your project: `hostel-management`
3. Click on "Connection Details"
4. You'll see two connection strings:

### Pooled Connection (Use this for your app):
```
postgresql://[username]:[password]@[host]-pooler.neon.tech/neondb?sslmode=require
```

### Direct Connection (Use for migrations):
```
postgresql://[username]:[password]@[host].neon.tech/neondb?sslmode=require
```

## Step 2: Reset Your Password (IMPORTANT)

Since your old password was exposed, you MUST reset it:

1. In Neon Console, go to Settings
2. Click "Reset Password"
3. Copy the new password
4. Save it securely

## Step 3: Configure Backend

### For Local Development

Create `backend/.env`:
```bash
DATABASE_URL=jdbc:postgresql://[your-host]-pooler.neon.tech/neondb?sslmode=require
PGUSER=[your-username]
PGPASSWORD=[your-new-password]
PGHOST=[your-host]-pooler.neon.tech
PGDATABASE=neondb
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits
```

### For Railway Deployment

Add these environment variables in Railway:

```bash
DATABASE_URL=postgresql://[username]:[password]@[host]-pooler.neon.tech/neondb?sslmode=require
PGUSER=[your-username]
PGPASSWORD=[your-new-password]
PGHOST=[your-host]-pooler.neon.tech
PGDATABASE=neondb
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
```

## Step 4: Initialize Database

Connect to your Neon database and run the schema:

```bash
# Connect using psql
psql "postgresql://[username]:[password]@[host]-pooler.neon.tech/neondb?sslmode=require"

# Or use Neon's SQL Editor in the console
```

Then run your `DATABASE.sql` file to create tables.

## Step 5: Test Connection

### Test from command line:
```bash
psql "postgresql://[username]:[password]@[host]-pooler.neon.tech/neondb?sslmode=require" -c "SELECT version();"
```

### Test from your app:
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Your Previous Connection Details (DO NOT USE - EXPOSED)

Your old host was: `ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech`
Your old username was: `neondb_owner`
Your old password was: `npg_RuAqosp25ZJz` (COMPROMISED - MUST RESET)

## Connection String Format

### For Spring Boot (JDBC):
```
jdbc:postgresql://[host]-pooler.neon.tech/neondb?sslmode=require
```

### For psql/Node.js/Python:
```
postgresql://[username]:[password]@[host]-pooler.neon.tech/neondb?sslmode=require
```

## Troubleshooting

### Connection Timeout
- Make sure you're using the pooled connection (`-pooler` in hostname)
- Check if your IP is allowed (Neon allows all by default)

### Authentication Failed
- Verify you reset the password
- Make sure you're using the new password
- Check username is correct (usually `neondb_owner`)

### SSL Error
- Always include `?sslmode=require` in connection string
- Neon requires SSL connections

## Security Checklist

- [ ] Password has been reset in Neon Console
- [ ] New password saved in password manager
- [ ] `.env` files created locally (not committed to Git)
- [ ] Railway environment variables updated
- [ ] Vercel environment variables updated (if needed)
- [ ] Old credentials removed from all documentation
- [ ] Connection tested successfully

## Need Help?

If you're having trouble:
1. Check Neon Console for connection status
2. Verify your password was reset
3. Test connection with psql first
4. Check application logs for specific errors

## Quick Reference

**Neon Console:** https://console.neon.tech
**Your Project:** hostel-management
**Database:** neondb
**Region:** us-east-1 (AWS)
**Connection Type:** Pooled (recommended)
