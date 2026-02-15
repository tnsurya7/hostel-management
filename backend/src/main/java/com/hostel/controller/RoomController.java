package com.hostel.controller;

import com.hostel.model.*;
import com.hostel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Room Management", description = "APIs for managing rooms, beds, allocations, maintenance, and change requests")
public class RoomController {
    
    private final RoomService roomService;
    
    // ============ ROOM ENDPOINTS ============
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create a new room")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }
    
    @GetMapping
    @Operation(summary = "Get all rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }
    
    @GetMapping("/number/{roomNumber}")
    @Operation(summary = "Get room by room number")
    public ResponseEntity<Room> getRoomByNumber(@PathVariable String roomNumber) {
        Room room = roomService.getRoomByNumber(roomNumber);
        return ResponseEntity.ok(room);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get rooms by status")
    public ResponseEntity<List<Room>> getRoomsByStatus(@PathVariable String status) {
        List<Room> rooms = roomService.getRoomsByStatus(status);
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/type/{roomType}")
    @Operation(summary = "Get rooms by type")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable String roomType) {
        List<Room> rooms = roomService.getRoomsByType(roomType);
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/floor/{floor}")
    @Operation(summary = "Get rooms by floor")
    public ResponseEntity<List<Room>> getRoomsByFloor(@PathVariable Integer floor) {
        List<Room> rooms = roomService.getRoomsByFloor(floor);
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/block/{block}")
    @Operation(summary = "Get rooms by block")
    public ResponseEntity<List<Room>> getRoomsByBlock(@PathVariable String block) {
        List<Room> rooms = roomService.getRoomsByBlock(block);
        return ResponseEntity.ok(rooms);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update room")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updatedRoom);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete room")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(Map.of("message", "Room deleted successfully"));
    }
    
    // ============ BED ENDPOINTS ============
    
    @GetMapping("/{roomId}/beds")
    @Operation(summary = "Get all beds in a room")
    public ResponseEntity<List<Bed>> getBedsByRoomId(@PathVariable Long roomId) {
        List<Bed> beds = roomService.getBedsByRoomId(roomId);
        return ResponseEntity.ok(beds);
    }
    
    @GetMapping("/{roomId}/beds/available")
    @Operation(summary = "Get available beds in a room")
    public ResponseEntity<List<Bed>> getAvailableBedsByRoomId(@PathVariable Long roomId) {
        List<Bed> beds = roomService.getAvailableBedsByRoomId(roomId);
        return ResponseEntity.ok(beds);
    }
    
    @GetMapping("/beds/{bedId}")
    @Operation(summary = "Get bed by ID")
    public ResponseEntity<Bed> getBedById(@PathVariable Long bedId) {
        Bed bed = roomService.getBedById(bedId);
        return ResponseEntity.ok(bed);
    }
    
    @PatchMapping("/beds/{bedId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update bed status")
    public ResponseEntity<Bed> updateBedStatus(
            @PathVariable Long bedId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        Bed bed = roomService.updateBedStatus(bedId, status);
        return ResponseEntity.ok(bed);
    }
    
    // ============ ROOM ALLOCATION ENDPOINTS ============
    
    @PostMapping("/allocations")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Allocate room to student")
    public ResponseEntity<RoomAllocation> allocateRoom(@Valid @RequestBody RoomAllocation allocation) {
        RoomAllocation createdAllocation = roomService.allocateRoom(allocation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAllocation);
    }
    
    @GetMapping("/allocations")
    @Operation(summary = "Get all room allocations")
    public ResponseEntity<List<RoomAllocation>> getAllAllocations() {
        List<RoomAllocation> allocations = roomService.getAllAllocations();
        return ResponseEntity.ok(allocations);
    }
    
    @GetMapping("/allocations/{id}")
    @Operation(summary = "Get allocation by ID")
    public ResponseEntity<RoomAllocation> getAllocationById(@PathVariable Long id) {
        RoomAllocation allocation = roomService.getAllocationById(id);
        return ResponseEntity.ok(allocation);
    }
    
    @GetMapping("/allocations/student/{studentId}")
    @Operation(summary = "Get allocations by student ID")
    public ResponseEntity<List<RoomAllocation>> getAllocationsByStudentId(@PathVariable Long studentId) {
        List<RoomAllocation> allocations = roomService.getAllocationsByStudentId(studentId);
        return ResponseEntity.ok(allocations);
    }
    
    @GetMapping("/allocations/room/{roomId}")
    @Operation(summary = "Get allocations by room ID")
    public ResponseEntity<List<RoomAllocation>> getAllocationsByRoomId(@PathVariable Long roomId) {
        List<RoomAllocation> allocations = roomService.getAllocationsByRoomId(roomId);
        return ResponseEntity.ok(allocations);
    }
    
    @DeleteMapping("/allocations/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Deallocate room")
    public ResponseEntity<Map<String, String>> deallocateRoom(@PathVariable Long id) {
        roomService.deallocateRoom(id);
        return ResponseEntity.ok(Map.of("message", "Room deallocated successfully"));
    }
    
    // ============ ROOM MAINTENANCE ENDPOINTS ============
    
    @PostMapping("/maintenance")
    @Operation(summary = "Create maintenance request")
    public ResponseEntity<RoomMaintenance> createMaintenanceRequest(@Valid @RequestBody RoomMaintenance maintenance) {
        RoomMaintenance createdMaintenance = roomService.createMaintenanceRequest(maintenance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaintenance);
    }
    
    @GetMapping("/maintenance")
    @Operation(summary = "Get all maintenance requests")
    public ResponseEntity<List<RoomMaintenance>> getAllMaintenanceRequests() {
        List<RoomMaintenance> maintenanceList = roomService.getAllMaintenanceRequests();
        return ResponseEntity.ok(maintenanceList);
    }
    
    @GetMapping("/maintenance/{id}")
    @Operation(summary = "Get maintenance request by ID")
    public ResponseEntity<RoomMaintenance> getMaintenanceById(@PathVariable Long id) {
        RoomMaintenance maintenance = roomService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }
    
    @GetMapping("/maintenance/room/{roomId}")
    @Operation(summary = "Get maintenance requests by room ID")
    public ResponseEntity<List<RoomMaintenance>> getMaintenanceByRoomId(@PathVariable Long roomId) {
        List<RoomMaintenance> maintenanceList = roomService.getMaintenanceByRoomId(roomId);
        return ResponseEntity.ok(maintenanceList);
    }
    
    @GetMapping("/maintenance/status/{status}")
    @Operation(summary = "Get maintenance requests by status")
    public ResponseEntity<List<RoomMaintenance>> getMaintenanceByStatus(@PathVariable String status) {
        List<RoomMaintenance> maintenanceList = roomService.getMaintenanceByStatus(status);
        return ResponseEntity.ok(maintenanceList);
    }
    
    @GetMapping("/maintenance/priority/{priority}")
    @Operation(summary = "Get maintenance requests by priority")
    public ResponseEntity<List<RoomMaintenance>> getMaintenanceByPriority(@PathVariable String priority) {
        List<RoomMaintenance> maintenanceList = roomService.getMaintenanceByPriority(priority);
        return ResponseEntity.ok(maintenanceList);
    }
    
    @PatchMapping("/maintenance/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update maintenance request status")
    public ResponseEntity<RoomMaintenance> updateMaintenanceStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        String assignedTo = request.get("assignedTo");
        String remarks = request.get("remarks");
        RoomMaintenance maintenance = roomService.updateMaintenanceStatus(id, status, assignedTo, remarks);
        return ResponseEntity.ok(maintenance);
    }
    
    @DeleteMapping("/maintenance/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete maintenance request")
    public ResponseEntity<Map<String, String>> deleteMaintenanceRequest(@PathVariable Long id) {
        roomService.deleteMaintenanceRequest(id);
        return ResponseEntity.ok(Map.of("message", "Maintenance request deleted successfully"));
    }
    
    // ============ ROOM CHANGE REQUEST ENDPOINTS ============
    
    @PostMapping("/change-requests")
    @Operation(summary = "Create room change request")
    public ResponseEntity<RoomChangeRequest> createRoomChangeRequest(@Valid @RequestBody RoomChangeRequest request) {
        RoomChangeRequest createdRequest = roomService.createRoomChangeRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }
    
    @GetMapping("/change-requests")
    @Operation(summary = "Get all room change requests")
    public ResponseEntity<List<RoomChangeRequest>> getAllRoomChangeRequests() {
        List<RoomChangeRequest> requests = roomService.getAllRoomChangeRequests();
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/change-requests/{id}")
    @Operation(summary = "Get room change request by ID")
    public ResponseEntity<RoomChangeRequest> getRoomChangeRequestById(@PathVariable Long id) {
        RoomChangeRequest request = roomService.getRoomChangeRequestById(id);
        return ResponseEntity.ok(request);
    }
    
    @GetMapping("/change-requests/student/{studentId}")
    @Operation(summary = "Get room change requests by student ID")
    public ResponseEntity<List<RoomChangeRequest>> getRoomChangeRequestsByStudentId(@PathVariable Long studentId) {
        List<RoomChangeRequest> requests = roomService.getRoomChangeRequestsByStudentId(studentId);
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/change-requests/status/{status}")
    @Operation(summary = "Get room change requests by status")
    public ResponseEntity<List<RoomChangeRequest>> getRoomChangeRequestsByStatus(@PathVariable String status) {
        List<RoomChangeRequest> requests = roomService.getRoomChangeRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }
    
    @PatchMapping("/change-requests/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Approve room change request")
    public ResponseEntity<RoomChangeRequest> approveRoomChangeRequest(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String approvedBy = request.get("approvedBy");
        String remarks = request.get("remarks");
        RoomChangeRequest updatedRequest = roomService.approveRoomChangeRequest(id, approvedBy, remarks);
        return ResponseEntity.ok(updatedRequest);
    }
    
    @PatchMapping("/change-requests/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Reject room change request")
    public ResponseEntity<RoomChangeRequest> rejectRoomChangeRequest(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String rejectedBy = request.get("rejectedBy");
        String remarks = request.get("remarks");
        RoomChangeRequest updatedRequest = roomService.rejectRoomChangeRequest(id, rejectedBy, remarks);
        return ResponseEntity.ok(updatedRequest);
    }
    
    @DeleteMapping("/change-requests/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete room change request")
    public ResponseEntity<Map<String, String>> deleteRoomChangeRequest(@PathVariable Long id) {
        roomService.deleteRoomChangeRequest(id);
        return ResponseEntity.ok(Map.of("message", "Room change request deleted successfully"));
    }
}
