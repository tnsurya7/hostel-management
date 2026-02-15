package com.hostel.repository;

import com.hostel.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {
    List<EmergencyContact> findByStudentId(Long studentId);
    Optional<EmergencyContact> findByStudentIdAndIsPrimary(Long studentId, Boolean isPrimary);
    List<EmergencyContact> findByStudentIdOrderByPriorityAsc(Long studentId);
}
