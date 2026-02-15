-- Hostel Management System Database Schema
-- PostgreSQL (Neon DB)

-- Drop table if exists (for fresh start)
DROP TABLE IF EXISTS students CASCADE;

-- Create students table
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    room_number VARCHAR(50),
    fees_paid BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_students_email ON students(email);
CREATE INDEX idx_students_room_number ON students(room_number);
CREATE INDEX idx_students_fees_paid ON students(fees_paid);
CREATE INDEX idx_students_created_at ON students(created_at);

-- Add comments for documentation
COMMENT ON TABLE students IS 'Stores hostel student information';
COMMENT ON COLUMN students.id IS 'Unique identifier for each student';
COMMENT ON COLUMN students.name IS 'Full name of the student';
COMMENT ON COLUMN students.email IS 'Unique email address of the student';
COMMENT ON COLUMN students.room_number IS 'Assigned room number';
COMMENT ON COLUMN students.fees_paid IS 'Fee payment status (true=paid, false=unpaid)';
COMMENT ON COLUMN students.created_at IS 'Timestamp when student record was created';

-- Insert sample data
INSERT INTO students (name, email, room_number, fees_paid) VALUES
('Alice Johnson', 'alice.johnson@example.com', '101', true),
('Bob Smith', 'bob.smith@example.com', '102', false),
('Charlie Brown', 'charlie.brown@example.com', '101', false),
('Diana Prince', 'diana.prince@example.com', '103', true),
('Eve Adams', 'eve.adams@example.com', '102', false),
('Frank Miller', 'frank.miller@example.com', '104', true),
('Grace Lee', 'grace.lee@example.com', '103', false),
('Henry Wilson', 'henry.wilson@example.com', '105', true),
('Ivy Chen', 'ivy.chen@example.com', '104', false),
('Jack Davis', 'jack.davis@example.com', '105', true);

-- Useful queries for testing and management

-- 1. Get all students
SELECT * FROM students ORDER BY created_at DESC;

-- 2. Get students who haven't paid fees
SELECT * FROM students WHERE fees_paid = false;

-- 3. Get students who have paid fees
SELECT * FROM students WHERE fees_paid = true;

-- 4. Get students by room number
SELECT * FROM students WHERE room_number = '101';

-- 5. Count total students
SELECT COUNT(*) as total_students FROM students;

-- 6. Count unpaid students
SELECT COUNT(*) as unpaid_students FROM students WHERE fees_paid = false;

-- 7. Get statistics
SELECT 
    COUNT(*) as total_students,
    COUNT(CASE WHEN fees_paid = true THEN 1 END) as paid_students,
    COUNT(CASE WHEN fees_paid = false THEN 1 END) as unpaid_students
FROM students;

-- 8. Get room occupancy
SELECT 
    room_number,
    COUNT(*) as student_count
FROM students
WHERE room_number IS NOT NULL
GROUP BY room_number
ORDER BY room_number;

-- 9. Get students added in last 7 days
SELECT * FROM students 
WHERE created_at >= NOW() - INTERVAL '7 days'
ORDER BY created_at DESC;

-- 10. Search students by name (case-insensitive)
SELECT * FROM students 
WHERE LOWER(name) LIKE LOWER('%john%');

-- 11. Get students with email domain
SELECT * FROM students 
WHERE email LIKE '%@example.com';

-- 12. Update fee status for a student
UPDATE students 
SET fees_paid = true 
WHERE id = 1;

-- 13. Update room number for a student
UPDATE students 
SET room_number = '106' 
WHERE id = 1;

-- 14. Delete a student
DELETE FROM students WHERE id = 1;

-- 15. Get students ordered by name
SELECT * FROM students ORDER BY name ASC;

-- Advanced queries for future enhancements

-- 16. Get rooms with unpaid students
SELECT DISTINCT room_number 
FROM students 
WHERE fees_paid = false AND room_number IS NOT NULL
ORDER BY room_number;

-- 17. Get average students per room
SELECT AVG(student_count) as avg_students_per_room
FROM (
    SELECT room_number, COUNT(*) as student_count
    FROM students
    WHERE room_number IS NOT NULL
    GROUP BY room_number
) as room_counts;

-- 18. Get rooms that are full (assuming max 2 students per room)
SELECT room_number, COUNT(*) as student_count
FROM students
WHERE room_number IS NOT NULL
GROUP BY room_number
HAVING COUNT(*) >= 2
ORDER BY room_number;

-- 19. Get monthly student additions
SELECT 
    DATE_TRUNC('month', created_at) as month,
    COUNT(*) as students_added
FROM students
GROUP BY DATE_TRUNC('month', created_at)
ORDER BY month DESC;

-- 20. Get fee collection rate
SELECT 
    ROUND(
        (COUNT(CASE WHEN fees_paid = true THEN 1 END)::DECIMAL / COUNT(*)) * 100,
        2
    ) as fee_collection_percentage
FROM students;

-- Maintenance queries

-- Reset all fees to unpaid (for testing)
-- UPDATE students SET fees_paid = false;

-- Delete all students (for fresh start)
-- DELETE FROM students;

-- Reset auto-increment counter
-- ALTER SEQUENCE students_id_seq RESTART WITH 1;

-- Backup query (export to CSV)
-- COPY students TO '/path/to/students_backup.csv' DELIMITER ',' CSV HEADER;

-- Restore from CSV
-- COPY students(name, email, room_number, fees_paid) 
-- FROM '/path/to/students_backup.csv' DELIMITER ',' CSV HEADER;

-- Performance analysis
-- EXPLAIN ANALYZE SELECT * FROM students WHERE email = 'test@example.com';

-- Check table size
SELECT 
    pg_size_pretty(pg_total_relation_size('students')) as total_size,
    pg_size_pretty(pg_relation_size('students')) as table_size,
    pg_size_pretty(pg_indexes_size('students')) as indexes_size;

-- Check index usage
SELECT 
    schemaname,
    tablename,
    indexname,
    idx_scan as index_scans,
    idx_tup_read as tuples_read,
    idx_tup_fetch as tuples_fetched
FROM pg_stat_user_indexes
WHERE tablename = 'students';
