package com.hostel.controller;

import com.hostel.model.Complaint;
import com.hostel.service.ComplaintService;
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
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Complaint Management", description = "APIs for managing student complaints and issues")
public class ComplaintController {
    
    private final ComplaintService complaintService;
    
    @PostMapping
    @Operation(summary = "Create a new complaint")
    public ResponseEntity<Complaint> createComplaint(@Valid @RequestBody Complaint complaint) {
        Complaint createdComplaint = complaintService.createComplaint(complaint);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComplaint);
    }
    
    @GetMapping
    @Operation(summary = "Get all complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get complaint by ID")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        return ResponseEntity.ok(complaint);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get complaints by student ID")
    public ResponseEntity<List<Complaint>> getComplaintsByStudentId(@PathVariable Long studentId) {
        List<Complaint> complaints = complaintService.getComplaintsByStudentId(studentId);
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get complaints by status")
    public ResponseEntity<List<Complaint>> getComplaintsByStatus(@PathVariable String status) {
        List<Complaint> complaints = complaintService.getComplaintsByStatus(status);
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "Get complaints by category")
    public ResponseEntity<List<Complaint>> getComplaintsByCategory(@PathVariable String category) {
        List<Complaint> complaints = complaintService.getComplaintsByCategory(category);
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get complaints by priority")
    public ResponseEntity<List<Complaint>> getComplaintsByPriority(@PathVariable String priority) {
        List<Complaint> complaints = complaintService.getComplaintsByPriority(priority);
        return ResponseEntity.ok(complaints);
    }
    
    @PatchMapping("/{id}/assign")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Assign complaint to staff")
    public ResponseEntity<Complaint> assignComplaint(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String assignedTo = request.get("assignedTo");
        Complaint complaint = complaintService.assignComplaint(id, assignedTo);
        return ResponseEntity.ok(complaint);
    }
    
    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Resolve complaint")
    public ResponseEntity<Complaint> resolveComplaint(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String resolution = request.get("resolution");
        Complaint complaint = complaintService.resolveComplaint(id, resolution);
        return ResponseEntity.ok(complaint);
    }
    
    @PatchMapping("/{id}/close")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Close complaint")
    public ResponseEntity<Complaint> closeComplaint(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String remarks = request.get("remarks");
        Complaint complaint = complaintService.closeComplaint(id, remarks);
        return ResponseEntity.ok(complaint);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update complaint")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable Long id, @Valid @RequestBody Complaint complaint) {
        Complaint updatedComplaint = complaintService.updateComplaint(id, complaint);
        return ResponseEntity.ok(updatedComplaint);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete complaint")
    public ResponseEntity<Map<String, String>> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok(Map.of("message", "Complaint deleted successfully"));
    }
}
