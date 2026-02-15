package com.hostel.repository;

import com.hostel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByStatus(String status);
    List<Room> findByRoomType(String roomType);
    List<Room> findByFloor(Integer floor);
    List<Room> findByBlock(String block);
    List<Room> findByStatusAndRoomType(String status, String roomType);
    boolean existsByRoomNumber(String roomNumber);
}
