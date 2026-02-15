package com.hostel.controller;

import com.hostel.model.EmergencyAlert;
import com.hostel.model.EmergencyContact;
import com.hostel.service.EmergencyService;
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
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Emergency Management", description = "APIs for managing emergency alerts and contacts")
public class EmergencyController {
    
    private final EmergencyService emergencyService;
    
    // ============ EMERGENCY ALERT ENDPOINTS ============
    
    @PostMapping("/alerts")
    @Operation(summary = "Create emergency alert")
    public ResponseEntity<EmergencyAlert> createAlert(@Valid @RequestBody EmergencyAlert alert) {
        EmergencyAlert createdAlert = emergencyService.createAlert(alert);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
    }
    
    @PostMapping("/alerts/sos")
    @Operation(summary = "Create SOS alert")
    public ResponseEntity<EmergencyAlert> createSOSAlert(@RequestBody Map<String, Object> request) {
        Long studentId = Long.valueOf(request.get("studentId").toString());
        String location = (String) request.get("location");
        String description = (String) request.get("description");
        EmergencyAlert alert = emergencyService.createSOSAlert(studentId, location, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(alert);
    }
    
    @GetMapping("/alerts")
    @Operation(summary = "Get all emergency alerts")
    public ResponseEntity<List<EmergencyAlert>> getAllAlerts() {
        List<EmergencyAlert> alerts = emergencyService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }
    
    @GetMapping("/alerts/{id}")
    @Operation(summary = "Get alert by ID")
    public ResponseEntity<EmergencyAlert> getAlertById(@PathVariable Long id) {
        EmergencyAlert alert = emergencyService.getAlertById(id);
        return ResponseEntity.ok(alert);
    }
    
    @GetMapping("/alerts/status/{status}")
    @Operation(summary = "Get alerts by status")
    public ResponseEntity<List<EmergencyAlert>> getAlertsByStatus(@PathVariable String status) {
        List<EmergencyAlert> alerts = emergencyService.getAlertsByStatus(status);
        return ResponseEntity.ok(alerts);
    }
    
    @GetMapping("/alerts/active")
    @Operation(summary = "Get active alerts")
    public ResponseEntity<List<EmergencyAlert>> getActiveAlerts() {
        List<EmergencyAlert> alerts = emergencyService.getActiveAlerts();
        return ResponseEntity.ok(alerts);
    }
    
    @GetMapping("/alerts/critical")
    @Operation(summary = "Get critical alerts")
    public ResponseEntity<List<EmergencyAlert>> getCriticalAlerts() {
        List<EmergencyAlert> alerts = emergencyService.getCriticalAlerts();
        return ResponseEntity.ok(alerts);
    }
    
    @PatchMapping("/alerts/{id}/acknowledge")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Acknowledge emergency alert")
    public ResponseEntity<EmergencyAlert> acknowledgeAlert(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String respondedBy = request.get("respondedBy");
        EmergencyAlert alert = emergencyService.acknowledgeAlert(id, respondedBy);
        return ResponseEntity.ok(alert);
    }
    
    @PatchMapping("/alerts/{id}/resolve")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Resolve emergency alert")
    public ResponseEntity<EmergencyAlert> resolveAlert(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String actionTaken = request.get("actionTaken");
        String remarks = request.get("remarks");
        EmergencyAlert alert = emergencyService.resolveAlert(id, actionTaken, remarks);
        return ResponseEntity.ok(alert);
    }
    
    @DeleteMapping("/alerts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete emergency alert")
    public ResponseEntity<Map<String, String>> deleteAlert(@PathVariable Long id) {
        emergencyService.deleteAlert(id);
        return ResponseEntity.ok(Map.of("message", "Emergency alert deleted successfully"));
    }
    
    // ============ EMERGENCY CONTACT ENDPOINTS ============
    
    @PostMapping("/contacts")
    @Operation(summary = "Add emergency contact")
    public ResponseEntity<EmergencyContact> addEmergencyContact(@Valid @RequestBody EmergencyContact contact) {
        EmergencyContact createdContact = emergencyService.addEmergencyContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }
    
    @GetMapping("/contacts")
    @Operation(summary = "Get all emergency contacts")
    public ResponseEntity<List<EmergencyContact>> getAllEmergencyContacts() {
        List<EmergencyContact> contacts = emergencyService.getAllEmergencyContacts();
        return ResponseEntity.ok(contacts);
    }
    
    @GetMapping("/contacts/{id}")
    @Operation(summary = "Get emergency contact by ID")
    public ResponseEntity<EmergencyContact> getEmergencyContactById(@PathVariable Long id) {
        EmergencyContact contact = emergencyService.getEmergencyContactById(id);
        return ResponseEntity.ok(contact);
    }
    
    @GetMapping("/contacts/student/{studentId}")
    @Operation(summary = "Get emergency contacts by student ID")
    public ResponseEntity<List<EmergencyContact>> getEmergencyContactsByStudentId(@PathVariable Long studentId) {
        List<EmergencyContact> contacts = emergencyService.getEmergencyContactsByStudentId(studentId);
        return ResponseEntity.ok(contacts);
    }
    
    @PutMapping("/contacts/{id}")
    @Operation(summary = "Update emergency contact")
    public ResponseEntity<EmergencyContact> updateEmergencyContact(
            @PathVariable Long id,
            @Valid @RequestBody EmergencyContact contact) {
        EmergencyContact updatedContact = emergencyService.updateEmergencyContact(id, contact);
        return ResponseEntity.ok(updatedContact);
    }
    
    @DeleteMapping("/contacts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete emergency contact")
    public ResponseEntity<Map<String, String>> deleteEmergencyContact(@PathVariable Long id) {
        emergencyService.deleteEmergencyContact(id);
        return ResponseEntity.ok(Map.of("message", "Emergency contact deleted successfully"));
    }
}
