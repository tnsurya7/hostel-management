# Security Fix - Exposed Database Credentials

## What Happened
GitGuardian detected exposed PostgreSQL credentials in your GitHub repository. The credentials were hardcoded in documentation and configuration files.

## What Was Fixed
All exposed credentials have been removed and replaced with placeholders or environment variables:

### Files Updated:
1. `backend/src/main/resources/application-prod.properties` - Now uses environment variables
2. `NEON_DEPLOYMENT_GUIDE.md` - Credentials replaced with placeholders
3. `ENV_VARIABLES.md` - Credentials replaced with placeholders
4. `WHAT_CHANGED.md` - Credentials replaced with placeholders
5. `DEPLOYMENT_COMPLETE_GUIDE.md` - Credentials replaced with placeholders

## CRITICAL: What You Must Do Now

### 1. Reset Your Neon Database Password (URGENT)
The exposed password `npg_RuAqosp25ZJz` is now public and must be changed:

1. Go to [Neon Console](https://console.neon.tech)
2. Select your project
3. Go to Settings → Reset Password
4. Generate a new password
5. Copy the new connection string

### 2. Update Your Deployment Environment Variables
After resetting the password, update these platforms:

**Railway (Backend):**
```bash
DATABASE_URL=postgresql://neondb_owner:NEW_PASSWORD@ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require
PGUSER=neondb_owner
PGPASSWORD=NEW_PASSWORD
PGHOST=ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
PGDATABASE=neondb
```

**Vercel (if using database directly):**
Update any database environment variables with the new credentials.

### 3. Verify Git History
The exposed credentials are still in your Git history. To completely remove them:

```bash
# Option 1: Use BFG Repo-Cleaner (recommended)
# Download from: https://rtyley.github.io/bfg-repo-cleaner/
java -jar bfg.jar --replace-text passwords.txt
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# Option 2: Use git-filter-repo
pip install git-filter-repo
git filter-repo --replace-text passwords.txt

# Then force push (WARNING: This rewrites history)
git push --force --all
```

Create a `passwords.txt` file with:
```
npg_RuAqosp25ZJz==>REMOVED
```

### 4. Best Practices Going Forward

**Never commit:**
- Database passwords
- API keys
- JWT secrets
- Any credentials

**Always use:**
- Environment variables for secrets
- `.env` files (added to `.gitignore`)
- Placeholder values in documentation
- Secret management services (AWS Secrets Manager, HashiCorp Vault, etc.)

**Your `.gitignore` already includes:**
```
backend/.env
frontend/.env*.local
```

### 5. Create Environment Files Locally

**Backend** (`backend/.env`):
```bash
DATABASE_URL=jdbc:postgresql://ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech/neondb?sslmode=require
PGUSER=neondb_owner
PGPASSWORD=YOUR_NEW_PASSWORD
PGHOST=ep-blue-queen-aifzk6u5-pooler.c-4.us-east-1.aws.neon.tech
PGDATABASE=neondb
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits
```

**Frontend** (`frontend/.env.local`):
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080
```

### 6. Monitor for Future Leaks

Consider installing:
- [git-secrets](https://github.com/awslabs/git-secrets) - Prevents committing secrets
- [pre-commit hooks](https://pre-commit.com/) - Scans before commits
- [GitGuardian CLI](https://github.com/GitGuardian/ggshield) - Local scanning

## Verification Checklist

- [ ] Reset Neon database password
- [ ] Update Railway environment variables
- [ ] Update Vercel environment variables (if applicable)
- [ ] Create local `.env` files (not committed)
- [ ] Test application with new credentials
- [ ] Consider cleaning Git history
- [ ] Install git-secrets or similar tool
- [ ] Commit and push these security fixes

## Questions?

If you need help with any of these steps, let me know!
