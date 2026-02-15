package com.hostel.controller;

import com.hostel.model.LeaveRequest;
import com.hostel.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave-requests")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Leave Request Management", description = "APIs for managing student leave requests")
public class LeaveRequestController {
    
    private final LeaveRequestService leaveRequestService;
    
    @PostMapping
    @Operation(summary = "Create a new leave request")
    public ResponseEntity<LeaveRequest> createLeaveRequest(@Valid @RequestBody LeaveRequest leaveRequest) {
        LeaveRequest created = leaveRequestService.createLeaveRequest(leaveRequest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "Get all leave requests")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        List<LeaveRequest> requests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get leave request by ID")
    public ResponseEntity<LeaveRequest> getLeaveRequestById(@PathVariable Long id) {
        LeaveRequest request = leaveRequestService.getLeaveRequestById(id);
        return ResponseEntity.ok(request);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get leave requests by student ID")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByStudent(@PathVariable Long studentId) {
        List<LeaveRequest> requests = leaveRequestService.getLeaveRequestsByStudent(studentId);
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get leave requests by status")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByStatus(@PathVariable String status) {
        List<LeaveRequest> requests = leaveRequestService.getLeaveRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }
    
    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve leave request")
    public ResponseEntity<LeaveRequest> approveLeaveRequest(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String approvedBy = payload.getOrDefault("approvedBy", "Admin");
        String remarks = payload.getOrDefault("remarks", "");
        LeaveRequest approved = leaveRequestService.approveLeaveRequest(id, approvedBy, remarks);
        return ResponseEntity.ok(approved);
    }
    
    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject leave request")
    public ResponseEntity<LeaveRequest> rejectLeaveRequest(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String rejectedBy = payload.getOrDefault("rejectedBy", "Admin");
        String remarks = payload.getOrDefault("remarks", "");
        LeaveRequest rejected = leaveRequestService.rejectLeaveRequest(id, rejectedBy, remarks);
        return ResponseEntity.ok(rejected);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete leave request")
    public ResponseEntity<Map<String, String>> deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Leave request deleted successfully");
        return ResponseEntity.ok(response);
    }
}
