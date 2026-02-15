package com.hostel.repository;

import com.hostel.model.RoomAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomAllocationRepository extends JpaRepository<RoomAllocation, Long> {
    List<RoomAllocation> findByStudentId(Long studentId);
    List<RoomAllocation> findByRoomId(Long roomId);
    List<RoomAllocation> findByStatus(String status);
    Optional<RoomAllocation> findByStudentIdAndStatus(Long studentId, String status);
    List<RoomAllocation> findByRoomIdAndStatus(Long roomId, String status);
}
