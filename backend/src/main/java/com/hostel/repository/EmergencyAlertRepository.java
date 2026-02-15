package com.hostel.repository;

import com.hostel.model.EmergencyAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyAlertRepository extends JpaRepository<EmergencyAlert, Long> {
    List<EmergencyAlert> findByStudentId(Long studentId);
    List<EmergencyAlert> findByStatus(String status);
    List<EmergencyAlert> findByAlertType(String alertType);
    List<EmergencyAlert> findBySeverity(String severity);
    List<EmergencyAlert> findByStatusAndSeverity(String status, String severity);
}
