package com.hostel.controller;

import com.hostel.model.GatePass;
import com.hostel.service.GatePassService;
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
@RequestMapping("/api/gate-passes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Gate Pass Management", description = "APIs for managing student gate passes")
public class GatePassController {
    
    private final GatePassService gatePassService;
    
    @PostMapping
    @Operation(summary = "Create a new gate pass")
    public ResponseEntity<GatePass> createGatePass(@Valid @RequestBody GatePass gatePass) {
        GatePass createdGatePass = gatePassService.createGatePass(gatePass);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGatePass);
    }
    
    @GetMapping
    @Operation(summary = "Get all gate passes")
    public ResponseEntity<List<GatePass>> getAllGatePasses() {
        List<GatePass> gatePasses = gatePassService.getAllGatePasses();
        return ResponseEntity.ok(gatePasses);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get gate pass by ID")
    public ResponseEntity<GatePass> getGatePassById(@PathVariable Long id) {
        GatePass gatePass = gatePassService.getGatePassById(id);
        return ResponseEntity.ok(gatePass);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get gate passes by student ID")
    public ResponseEntity<List<GatePass>> getGatePassesByStudentId(@PathVariable Long studentId) {
        List<GatePass> gatePasses = gatePassService.getGatePassesByStudentId(studentId);
        return ResponseEntity.ok(gatePasses);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get gate passes by status")
    public ResponseEntity<List<GatePass>> getGatePassesByStatus(@PathVariable String status) {
        List<GatePass> gatePasses = gatePassService.getGatePassesByStatus(status);
        return ResponseEntity.ok(gatePasses);
    }
    
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Approve gate pass")
    public ResponseEntity<GatePass> approveGatePass(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String approvedBy = request.get("approvedBy");
        GatePass gatePass = gatePassService.approveGatePass(id, approvedBy);
        return ResponseEntity.ok(gatePass);
    }
    
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Reject gate pass")
    public ResponseEntity<GatePass> rejectGatePass(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String rejectedBy = request.get("rejectedBy");
        String remarks = request.get("remarks");
        GatePass gatePass = gatePassService.rejectGatePass(id, rejectedBy, remarks);
        return ResponseEntity.ok(gatePass);
    }
    
    @PatchMapping("/{id}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Mark student return")
    public ResponseEntity<GatePass> markReturn(@PathVariable Long id) {
        GatePass gatePass = gatePassService.markReturn(id);
        return ResponseEntity.ok(gatePass);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete gate pass")
    public ResponseEntity<Map<String, String>> deleteGatePass(@PathVariable Long id) {
        gatePassService.deleteGatePass(id);
        return ResponseEntity.ok(Map.of("message", "Gate pass deleted successfully"));
    }
}
