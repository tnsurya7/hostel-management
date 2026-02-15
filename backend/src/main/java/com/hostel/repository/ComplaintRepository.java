package com.hostel.repository;

import com.hostel.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStudentId(Long studentId);
    List<Complaint> findByStatus(String status);
    List<Complaint> findByCategory(String category);
    List<Complaint> findByPriority(String priority);
    List<Complaint> findByAssignedTo(String assignedTo);
    List<Complaint> findByStatusAndPriority(String status, String priority);
}
