package com.hostel.controller;

import com.hostel.model.Attendance;
import com.hostel.service.AttendanceService;
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
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Attendance Management", description = "APIs for managing student attendance")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Mark attendance")
    public ResponseEntity<Attendance> markAttendance(@Valid @RequestBody Attendance attendance) {
        Attendance markedAttendance = attendanceService.markAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(markedAttendance);
    }
    
    @PostMapping("/check-in")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Check in student")
    public ResponseEntity<Attendance> checkIn(@RequestBody Map<String, Object> request) {
        Long studentId = Long.valueOf(request.get("studentId").toString());
        LocalDate date = LocalDate.parse(request.get("date").toString());
        Attendance attendance = attendanceService.checkIn(studentId, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }
    
    @GetMapping
    @Operation(summary = "Get all attendance records")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendanceList);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get attendance by ID")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get attendance by student ID")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable Long studentId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudentId(studentId);
        return ResponseEntity.ok(attendanceList);
    }
    
    @GetMapping("/date/{date}")
    @Operation(summary = "Get attendance by date")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(attendanceList);
    }
    
    @GetMapping("/student/{studentId}/percentage")
    @Operation(summary = "Get attendance percentage")
    public ResponseEntity<Map<String, Long>> getAttendancePercentage(@PathVariable Long studentId) {
        Long percentage = attendanceService.getAttendancePercentage(studentId);
        return ResponseEntity.ok(Map.of("percentage", percentage));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete attendance")
    public ResponseEntity<Map<String, String>> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok(Map.of("message", "Attendance deleted successfully"));
    }
}
