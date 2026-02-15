package com.hostel.controller;

import com.hostel.model.LaundryRequest;
import com.hostel.service.LaundryService;
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
@RequestMapping("/api/laundry")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Laundry Management", description = "APIs for managing laundry requests")
public class LaundryController {
    
    private final LaundryService laundryService;
    
    @PostMapping("/requests")
    @Operation(summary = "Create laundry request")
    public ResponseEntity<LaundryRequest> createRequest(@Valid @RequestBody LaundryRequest request) {
        LaundryRequest createdRequest = laundryService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }
    
    @GetMapping("/requests")
    @Operation(summary = "Get all laundry requests")
    public ResponseEntity<List<LaundryRequest>> getAllRequests() {
        List<LaundryRequest> requests = laundryService.getAllRequests();
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/requests/{id}")
    @Operation(summary = "Get laundry request by ID")
    public ResponseEntity<LaundryRequest> getRequestById(@PathVariable Long id) {
        LaundryRequest request = laundryService.getRequestById(id);
        return ResponseEntity.ok(request);
    }
    
    @GetMapping("/requests/student/{studentId}")
    @Operation(summary = "Get laundry requests by student ID")
    public ResponseEntity<List<LaundryRequest>> getRequestsByStudentId(@PathVariable Long studentId) {
        List<LaundryRequest> requests = laundryService.getRequestsByStudentId(studentId);
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/requests/status/{status}")
    @Operation(summary = "Get laundry requests by status")
    public ResponseEntity<List<LaundryRequest>> getRequestsByStatus(@PathVariable String status) {
        List<LaundryRequest> requests = laundryService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }
    
    @PatchMapping("/requests/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update laundry request status")
    public ResponseEntity<LaundryRequest> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        LaundryRequest updatedRequest = laundryService.updateStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }
    
    @PatchMapping("/requests/{id}/pay")
    @Operation(summary = "Mark laundry request as paid")
    public ResponseEntity<LaundryRequest> markPaid(@PathVariable Long id) {
        LaundryRequest request = laundryService.markPaid(id);
        return ResponseEntity.ok(request);
    }
    
    @DeleteMapping("/requests/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete laundry request")
    public ResponseEntity<Map<String, String>> deleteRequest(@PathVariable Long id) {
        laundryService.deleteRequest(id);
        return ResponseEntity.ok(Map.of("message", "Laundry request deleted successfully"));
    }
}
