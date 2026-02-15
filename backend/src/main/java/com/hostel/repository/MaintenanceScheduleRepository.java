package com.hostel.repository;

import com.hostel.model.MaintenanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {
    List<MaintenanceSchedule> findByStatus(String status);
    List<MaintenanceSchedule> findByMaintenanceType(String maintenanceType);
    List<MaintenanceSchedule> findByCategory(String category);
    List<MaintenanceSchedule> findByAssignedTo(String assignedTo);
    List<MaintenanceSchedule> findByScheduledDate(LocalDate scheduledDate);
    List<MaintenanceSchedule> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
}
