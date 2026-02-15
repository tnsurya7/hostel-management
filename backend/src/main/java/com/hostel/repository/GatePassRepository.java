package com.hostel.repository;

import com.hostel.model.GatePass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GatePassRepository extends JpaRepository<GatePass, Long> {
    List<GatePass> findByStudentId(Long studentId);
    List<GatePass> findByStatus(String status);
    List<GatePass> findByPassType(String passType);
    List<GatePass> findByLateReturn(Boolean lateReturn);
    List<GatePass> findByFromTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<GatePass> findByStudentIdAndStatus(Long studentId, String status);
}
