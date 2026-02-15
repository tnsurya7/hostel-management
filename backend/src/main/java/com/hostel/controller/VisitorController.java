package com.hostel.controller;

import com.hostel.model.Visitor;
import com.hostel.service.VisitorService;
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
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Visitor Management", description = "APIs for managing visitor registrations and passes")
public class VisitorController {
    
    private final VisitorService visitorService;
    
    @PostMapping
    @Operation(summary = "Register a new visitor")
    public ResponseEntity<Visitor> createVisitor(@Valid @RequestBody Visitor visitor) {
        Visitor createdVisitor = visitorService.createVisitor(visitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisitor);
    }
    
    @GetMapping
    @Operation(summary = "Get all visitors")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(visitors);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get visitor by ID")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable Long id) {
        Visitor visitor = visitorService.getVisitorById(id);
        return ResponseEntity.ok(visitor);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get visitors by student ID")
    public ResponseEntity<List<Visitor>> getVisitorsByStudentId(@PathVariable Long studentId) {
        List<Visitor> visitors = visitorService.getVisitorsByStudentId(studentId);
        return ResponseEntity.ok(visitors);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get visitors by status")
    public ResponseEntity<List<Visitor>> getVisitorsByStatus(@PathVariable String status) {
        List<Visitor> visitors = visitorService.getVisitorsByStatus(status);
        return ResponseEntity.ok(visitors);
    }
    
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Approve visitor")
    public ResponseEntity<Visitor> approveVisitor(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String approvedBy = request.get("approvedBy");
        Visitor visitor = visitorService.approveVisitor(id, approvedBy);
        return ResponseEntity.ok(visitor);
    }
    
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Reject visitor")
    public ResponseEntity<Visitor> rejectVisitor(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String rejectedBy = request.get("rejectedBy");
        String remarks = request.get("remarks");
        Visitor visitor = visitorService.rejectVisitor(id, rejectedBy, remarks);
        return ResponseEntity.ok(visitor);
    }
    
    @PatchMapping("/{id}/entry")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Mark visitor entry")
    public ResponseEntity<Visitor> markEntry(@PathVariable Long id) {
        Visitor visitor = visitorService.markEntry(id);
        return ResponseEntity.ok(visitor);
    }
    
    @PatchMapping("/{id}/exit")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Mark visitor exit")
    public ResponseEntity<Visitor> markExit(@PathVariable Long id) {
        Visitor visitor = visitorService.markExit(id);
        return ResponseEntity.ok(visitor);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete visitor")
    public ResponseEntity<Map<String, String>> deleteVisitor(@PathVariable Long id) {
        visitorService.deleteVisitor(id);
        return ResponseEntity.ok(Map.of("message", "Visitor deleted successfully"));
    }
}
