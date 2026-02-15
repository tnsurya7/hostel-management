package com.hostel.repository;

import com.hostel.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStudentId(Long studentId);
    List<LeaveRequest> findByStatus(String status);
    List<LeaveRequest> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}
