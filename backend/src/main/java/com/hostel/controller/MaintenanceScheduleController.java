package com.hostel.controller;

import com.hostel.model.MaintenanceSchedule;
import com.hostel.service.MaintenanceScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance-schedules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Maintenance Scheduling", description = "APIs for managing maintenance schedules")
public class MaintenanceScheduleController {
    
    private final MaintenanceScheduleService maintenanceScheduleService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create maintenance schedule")
    public ResponseEntity<MaintenanceSchedule> createSchedule(@Valid @RequestBody MaintenanceSchedule schedule) {
        MaintenanceSchedule createdSchedule = maintenanceScheduleService.createSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }
    
    @GetMapping
    @Operation(summary = "Get all maintenance schedules")
    public ResponseEntity<List<MaintenanceSchedule>> getAllSchedules() {
        List<MaintenanceSchedule> schedules = maintenanceScheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get maintenance schedule by ID")
    public ResponseEntity<MaintenanceSchedule> getScheduleById(@PathVariable Long id) {
        MaintenanceSchedule schedule = maintenanceScheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get schedules by status")
    public ResponseEntity<List<MaintenanceSchedule>> getSchedulesByStatus(@PathVariable String status) {
        List<MaintenanceSchedule> schedules = maintenanceScheduleService.getSchedulesByStatus(status);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/date/{date}")
    @Operation(summary = "Get schedules by date")
    public ResponseEntity<List<MaintenanceSchedule>> getSchedulesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MaintenanceSchedule> schedules = maintenanceScheduleService.getSchedulesByDate(date);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/today")
    @Operation(summary = "Get today's schedules")
    public ResponseEntity<List<MaintenanceSchedule>> getTodaySchedules() {
        List<MaintenanceSchedule> schedules = maintenanceScheduleService.getTodaySchedules();
        return ResponseEntity.ok(schedules);
    }
    
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update schedule status")
    public ResponseEntity<MaintenanceSchedule> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        MaintenanceSchedule schedule = maintenanceScheduleService.updateStatus(id, status);
        return ResponseEntity.ok(schedule);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update maintenance schedule")
    public ResponseEntity<MaintenanceSchedule> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceSchedule schedule) {
        MaintenanceSchedule updatedSchedule = maintenanceScheduleService.updateSchedule(id, schedule);
        return ResponseEntity.ok(updatedSchedule);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete maintenance schedule")
    public ResponseEntity<Map<String, String>> deleteSchedule(@PathVariable Long id) {
        maintenanceScheduleService.deleteSchedule(id);
        return ResponseEntity.ok(Map.of("message", "Maintenance schedule deleted successfully"));
    }
}
