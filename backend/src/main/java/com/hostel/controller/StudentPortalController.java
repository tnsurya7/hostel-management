package com.hostel.controller;

import com.hostel.model.*;
import com.hostel.service.StudentPortalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-portal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Student Portal", description = "APIs for student dashboard and profile")
public class StudentPortalController {
    
    private final StudentPortalService studentPortalService;
    
    @GetMapping("/dashboard/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(summary = "Get student dashboard")
    public ResponseEntity<StudentDashboard> getStudentDashboard(@PathVariable Long studentId) {
        StudentDashboard dashboard = studentPortalService.getStudentDashboard(studentId);
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/profile/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(summary = "Get student profile")
    public ResponseEntity<Student> getStudentProfile(@PathVariable Long studentId) {
        Student student = studentPortalService.getStudentProfile(studentId);
        return ResponseEntity.ok(student);
    }
    
    @GetMapping("/{studentId}/fees")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(summary = "Get student fees")
    public ResponseEntity<List<FeePayment>> getStudentFees(@PathVariable Long studentId) {
        List<FeePayment> fees = studentPortalService.getStudentFees(studentId);
        return ResponseEntity.ok(fees);
    }
    
    @GetMapping("/{studentId}/leave-requests")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(summary = "Get student leave requests")
    public ResponseEntity<List<LeaveRequest>> getStudentLeaveRequests(@PathVariable Long studentId) {
        List<LeaveRequest> leaveRequests = studentPortalService.getStudentLeaveRequests(studentId);
        return ResponseEntity.ok(leaveRequests);
    }
    
    @GetMapping("/{studentId}/complaints")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(summary = "Get student complaints")
    public ResponseEntity<List<Complaint>> getStudentComplaints(@PathVariable Long studentId) {
        List<Complaint> complaints = studentPortalService.getStudentComplaints(studentId);
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/{studentId}/gate-passes")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(summary = "Get student gate passes")
    public ResponseEntity<List<GatePass>> getStudentGatePasses(@PathVariable Long studentId) {
        List<GatePass> gatePasses = studentPortalService.getStudentGatePasses(studentId);
        return ResponseEntity.ok(gatePasses);
    }
}
