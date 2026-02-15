package com.hostel.service;

import com.hostel.exception.DuplicateResourceException;
import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.*;
import com.hostel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final RoomAllocationRepository roomAllocationRepository;
    private final RoomMaintenanceRepository roomMaintenanceRepository;
    private final RoomChangeRequestRepository roomChangeRequestRepository;
    private final StudentRepository studentRepository;
    
    // ============ ROOM OPERATIONS ============
    
    @Transactional
    public Room createRoom(Room room) {
        if (roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            throw new DuplicateResourceException("Room with number " + room.getRoomNumber() + " already exists");
        }
        room.setCurrentOccupancy(0);
        room.setStatus("AVAILABLE");
        Room savedRoom = roomRepository.save(room);
        
        // Auto-create beds based on capacity
        for (int i = 1; i <= room.getCapacity(); i++) {
            Bed bed = new Bed();
            bed.setRoomId(savedRoom.getId());
            bed.setBedNumber("B" + i);
            bed.setIsOccupied(false);
            bed.setStatus("AVAILABLE");
            bedRepository.save(bed);
        }
        
        return savedRoom;
    }
    
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }
    
    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with number: " + roomNumber));
    }
    
    public List<Room> getRoomsByStatus(String status) {
        return roomRepository.findByStatus(status);
    }
    
    public List<Room> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType);
    }
    
    public List<Room> getRoomsByFloor(Integer floor) {
        return roomRepository.findByFloor(floor);
    }
    
    public List<Room> getRoomsByBlock(String block) {
        return roomRepository.findByBlock(block);
    }
    
    @Transactional
    public Room updateRoom(Long id, Room roomDetails) {
        Room room = getRoomById(id);
        
        if (!room.getRoomNumber().equals(roomDetails.getRoomNumber()) && 
            roomRepository.existsByRoomNumber(roomDetails.getRoomNumber())) {
            throw new DuplicateResourceException("Room with number " + roomDetails.getRoomNumber() + " already exists");
        }
        
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setRoomType(roomDetails.getRoomType());
        room.setCapacity(roomDetails.getCapacity());
        room.setFloor(roomDetails.getFloor());
        room.setBlock(roomDetails.getBlock());
        room.setStatus(roomDetails.getStatus());
        room.setRentAmount(roomDetails.getRentAmount());
        room.setHasAc(roomDetails.getHasAc());
        room.setHasAttachedBathroom(roomDetails.getHasAttachedBathroom());
        room.setDescription(roomDetails.getDescription());
        
        return roomRepository.save(room);
    }
    
    @Transactional
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        
        // Check if room has active allocations
        List<RoomAllocation> activeAllocations = roomAllocationRepository.findByRoomIdAndStatus(id, "ACTIVE");
        if (!activeAllocations.isEmpty()) {
            throw new IllegalStateException("Cannot delete room with active allocations");
        }
        
        // Delete associated beds
        List<Bed> beds = bedRepository.findByRoomId(id);
        bedRepository.deleteAll(beds);
        
        roomRepository.delete(room);
    }
    
    // ============ BED OPERATIONS ============
    
    public List<Bed> getBedsByRoomId(Long roomId) {
        return bedRepository.findByRoomId(roomId);
    }
    
    public List<Bed> getAvailableBedsByRoomId(Long roomId) {
        return bedRepository.findByRoomIdAndStatus(roomId, "AVAILABLE");
    }
    
    public Bed getBedById(Long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bed not found with id: " + id));
    }
    
    @Transactional
    public Bed updateBedStatus(Long bedId, String status) {
        Bed bed = getBedById(bedId);
        bed.setStatus(status);
        return bedRepository.save(bed);
    }
    
    // ============ ROOM ALLOCATION OPERATIONS ============
    
    @Transactional
    public RoomAllocation allocateRoom(RoomAllocation allocation) {
        // Validate student exists
        Student student = studentRepository.findById(allocation.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + allocation.getStudentId()));
        
        // Check if student already has active allocation
        roomAllocationRepository.findByStudentIdAndStatus(allocation.getStudentId(), "ACTIVE")
                .ifPresent(existing -> {
                    throw new IllegalStateException("Student already has an active room allocation");
                });
        
        // Validate room exists and has capacity
        Room room = getRoomById(allocation.getRoomId());
        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            throw new IllegalStateException("Room is full");
        }
        
        // Find available bed
        List<Bed> availableBeds = getAvailableBedsByRoomId(allocation.getRoomId());
        if (availableBeds.isEmpty()) {
            throw new IllegalStateException("No available beds in the room");
        }
        
        Bed bed = availableBeds.get(0);
        
        // Update allocation details
        allocation.setStudentName(student.getName());
        allocation.setRoomNumber(room.getRoomNumber());
        allocation.setBedId(bed.getId());
        allocation.setBedNumber(bed.getBedNumber());
        allocation.setStatus("ACTIVE");
        if (allocation.getFromDate() == null) {
            allocation.setFromDate(LocalDate.now());
        }
        
        // Update bed
        bed.setIsOccupied(true);
        bed.setStudentId(student.getId());
        bed.setStudentName(student.getName());
        bed.setStatus("OCCUPIED");
        bedRepository.save(bed);
        
        // Update room occupancy
        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            room.setStatus("FULL");
        } else {
            room.setStatus("OCCUPIED");
        }
        roomRepository.save(room);
        
        // Update student room info
        student.setRoomNumber(room.getRoomNumber());
        studentRepository.save(student);
        
        return roomAllocationRepository.save(allocation);
    }
    
    public List<RoomAllocation> getAllAllocations() {
        return roomAllocationRepository.findAll();
    }
    
    public List<RoomAllocation> getAllocationsByStudentId(Long studentId) {
        return roomAllocationRepository.findByStudentId(studentId);
    }
    
    public List<RoomAllocation> getAllocationsByRoomId(Long roomId) {
        return roomAllocationRepository.findByRoomId(roomId);
    }
    
    public RoomAllocation getAllocationById(Long id) {
        return roomAllocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room allocation not found with id: " + id));
    }
    
    @Transactional
    public void deallocateRoom(Long allocationId) {
        RoomAllocation allocation = getAllocationById(allocationId);
        
        if (!"ACTIVE".equals(allocation.getStatus())) {
            throw new IllegalStateException("Allocation is not active");
        }
        
        // Update allocation
        allocation.setStatus("COMPLETED");
        allocation.setToDate(LocalDate.now());
        roomAllocationRepository.save(allocation);
        
        // Update bed
        if (allocation.getBedId() != null) {
            Bed bed = getBedById(allocation.getBedId());
            bed.setIsOccupied(false);
            bed.setStudentId(null);
            bed.setStudentName(null);
            bed.setStatus("AVAILABLE");
            bedRepository.save(bed);
        }
        
        // Update room occupancy
        Room room = getRoomById(allocation.getRoomId());
        room.setCurrentOccupancy(Math.max(0, room.getCurrentOccupancy() - 1));
        if (room.getCurrentOccupancy() == 0) {
            room.setStatus("AVAILABLE");
        } else {
            room.setStatus("OCCUPIED");
        }
        roomRepository.save(room);
        
        // Update student
        Student student = studentRepository.findById(allocation.getStudentId())
                .orElse(null);
        if (student != null) {
            student.setRoomNumber(null);
            studentRepository.save(student);
        }
    }
    
    // ============ ROOM MAINTENANCE OPERATIONS ============
    
    public RoomMaintenance createMaintenanceRequest(RoomMaintenance maintenance) {
        Room room = getRoomById(maintenance.getRoomId());
        maintenance.setRoomNumber(room.getRoomNumber());
        maintenance.setStatus("REPORTED");
        return roomMaintenanceRepository.save(maintenance);
    }
    
    public List<RoomMaintenance> getAllMaintenanceRequests() {
        return roomMaintenanceRepository.findAll();
    }
    
    public List<RoomMaintenance> getMaintenanceByRoomId(Long roomId) {
        return roomMaintenanceRepository.findByRoomId(roomId);
    }
    
    public List<RoomMaintenance> getMaintenanceByStatus(String status) {
        return roomMaintenanceRepository.findByStatus(status);
    }
    
    public List<RoomMaintenance> getMaintenanceByPriority(String priority) {
        return roomMaintenanceRepository.findByPriority(priority);
    }
    
    public RoomMaintenance getMaintenanceById(Long id) {
        return roomMaintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance request not found with id: " + id));
    }
    
    @Transactional
    public RoomMaintenance updateMaintenanceStatus(Long id, String status, String assignedTo, String remarks) {
        RoomMaintenance maintenance = getMaintenanceById(id);
        maintenance.setStatus(status);
        
        if (assignedTo != null) {
            maintenance.setAssignedTo(assignedTo);
            maintenance.setAssignedDate(LocalDateTime.now());
        }
        
        if (remarks != null) {
            maintenance.setRemarks(remarks);
        }
        
        if ("COMPLETED".equals(status)) {
            maintenance.setCompletedDate(LocalDateTime.now());
        }
        
        return roomMaintenanceRepository.save(maintenance);
    }
    
    @Transactional
    public void deleteMaintenanceRequest(Long id) {
        RoomMaintenance maintenance = getMaintenanceById(id);
        roomMaintenanceRepository.delete(maintenance);
    }
    
    // ============ ROOM CHANGE REQUEST OPERATIONS ============
    
    public RoomChangeRequest createRoomChangeRequest(RoomChangeRequest request) {
        // Validate student
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));
        
        // Validate current room
        Room currentRoom = getRoomById(request.getCurrentRoomId());
        
        request.setStudentName(student.getName());
        request.setCurrentRoomNumber(currentRoom.getRoomNumber());
        request.setStatus("PENDING");
        
        // If requested room is specified, validate it
        if (request.getRequestedRoomId() != null) {
            Room requestedRoom = getRoomById(request.getRequestedRoomId());
            request.setRequestedRoomNumber(requestedRoom.getRoomNumber());
            
            if (requestedRoom.getCurrentOccupancy() >= requestedRoom.getCapacity()) {
                throw new IllegalStateException("Requested room is full");
            }
        }
        
        return roomChangeRequestRepository.save(request);
    }
    
    public List<RoomChangeRequest> getAllRoomChangeRequests() {
        return roomChangeRequestRepository.findAll();
    }
    
    public List<RoomChangeRequest> getRoomChangeRequestsByStudentId(Long studentId) {
        return roomChangeRequestRepository.findByStudentId(studentId);
    }
    
    public List<RoomChangeRequest> getRoomChangeRequestsByStatus(String status) {
        return roomChangeRequestRepository.findByStatus(status);
    }
    
    public RoomChangeRequest getRoomChangeRequestById(Long id) {
        return roomChangeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room change request not found with id: " + id));
    }
    
    @Transactional
    public RoomChangeRequest approveRoomChangeRequest(Long id, String approvedBy, String remarks) {
        RoomChangeRequest request = getRoomChangeRequestById(id);
        
        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("Request is not pending");
        }
        
        request.setStatus("APPROVED");
        request.setApprovedBy(approvedBy);
        request.setApprovalDate(LocalDateTime.now());
        request.setRemarks(remarks);
        
        return roomChangeRequestRepository.save(request);
    }
    
    @Transactional
    public RoomChangeRequest rejectRoomChangeRequest(Long id, String rejectedBy, String remarks) {
        RoomChangeRequest request = getRoomChangeRequestById(id);
        
        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("Request is not pending");
        }
        
        request.setStatus("REJECTED");
        request.setApprovedBy(rejectedBy);
        request.setApprovalDate(LocalDateTime.now());
        request.setRemarks(remarks);
        
        return roomChangeRequestRepository.save(request);
    }
    
    @Transactional
    public void deleteRoomChangeRequest(Long id) {
        RoomChangeRequest request = getRoomChangeRequestById(id);
        roomChangeRequestRepository.delete(request);
    }
}
