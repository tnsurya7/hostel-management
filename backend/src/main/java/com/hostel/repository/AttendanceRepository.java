package com.hostel.repository;

import com.hostel.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByStatus(String status);
    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);
    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.status = 'PRESENT'")
    Long countPresentDays(Long studentId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.status = 'ABSENT'")
    Long countAbsentDays(Long studentId);
}
