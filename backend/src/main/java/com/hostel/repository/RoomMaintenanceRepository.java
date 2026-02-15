package com.hostel.repository;

import com.hostel.model.RoomMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMaintenanceRepository extends JpaRepository<RoomMaintenance, Long> {
    List<RoomMaintenance> findByRoomId(Long roomId);
    List<RoomMaintenance> findByStatus(String status);
    List<RoomMaintenance> findByPriority(String priority);
    List<RoomMaintenance> findByCategory(String category);
    List<RoomMaintenance> findByAssignedTo(String assignedTo);
    List<RoomMaintenance> findByStatusAndPriority(String status, String priority);
}
