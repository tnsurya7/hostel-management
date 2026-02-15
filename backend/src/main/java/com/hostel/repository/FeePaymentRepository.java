package com.hostel.repository;

import com.hostel.model.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    List<FeePayment> findByStudentId(Long studentId);
    List<FeePayment> findByStatus(String status);
    List<FeePayment> findByFeeTypeId(Long feeTypeId);
    List<FeePayment> findByStudentIdAndStatus(Long studentId, String status);
    List<FeePayment> findByDueDateBefore(LocalDate date);
    List<FeePayment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
    List<FeePayment> findBySemester(String semester);
    List<FeePayment> findByAcademicYear(String academicYear);
    
    @Query("SELECT SUM(f.totalAmount) FROM FeePayment f WHERE f.studentId = :studentId AND f.status = 'PAID'")
    Double getTotalPaidByStudent(Long studentId);
    
    @Query("SELECT SUM(f.totalAmount) FROM FeePayment f WHERE f.studentId = :studentId AND f.status = 'PENDING'")
    Double getTotalPendingByStudent(Long studentId);
}
