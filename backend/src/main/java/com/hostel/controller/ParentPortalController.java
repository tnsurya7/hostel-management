package com.hostel.controller;

import com.hostel.model.LeaveRequest;
import com.hostel.model.ParentAccess;
import com.hostel.model.ParentDashboard;
import com.hostel.model.Student;
import com.hostel.service.ParentPortalService;
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
@RequestMapping("/api/parent-portal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Parent Portal", description = "APIs for parent access and dashboard")
public class ParentPortalController {
    
    private final ParentPortalService parentPortalService;
    
    @PostMapping("/access")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create parent access")
    public ResponseEntity<ParentAccess> createParentAccess(@Valid @RequestBody ParentAccess parentAccess) {
        ParentAccess createdAccess = parentPortalService.createParentAccess(parentAccess);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccess);
    }
    
    @GetMapping("/access/parent/{parentUserId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARENT')")
    @Operation(summary = "Get parent access by parent user ID")
    public ResponseEntity<List<ParentAccess>> getParentAccessByParentUserId(@PathVariable Long parentUserId) {
        List<ParentAccess> accessList = parentPortalService.getParentAccessByParentUserId(parentUserId);
        return ResponseEntity.ok(accessList);
    }
    
    @GetMapping("/students/parent/{parentUserId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARENT')")
    @Operation(summary = "Get students by parent user ID")
    public ResponseEntity<List<Student>> getStudentsByParentUserId(@PathVariable Long parentUserId) {
        List<Student> students = parentPortalService.getStudentsByParentUserId(parentUserId);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/dashboard/{parentUserId}/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARENT')")
    @Operation(summary = "Get parent dashboard")
    public ResponseEntity<ParentDashboard> getParentDashboard(
            @PathVariable Long parentUserId,
            @PathVariable Long studentId) {
        ParentDashboard dashboard = parentPortalService.getParentDashboard(parentUserId, studentId);
        return ResponseEntity.ok(dashboard);
    }
    
    @PatchMapping("/leave-requests/{leaveRequestId}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARENT')")
    @Operation(summary = "Approve leave request by parent")
    public ResponseEntity<LeaveRequest> approveLeaveRequest(
            @PathVariable Long leaveRequestId,
            @RequestBody Map<String, Object> request) {
        Long parentUserId = Long.valueOf(request.get("parentUserId").toString());
        String remarks = (String) request.get("remarks");
        LeaveRequest leaveRequest = parentPortalService.approveLeaveRequest(parentUserId, leaveRequestId, remarks);
        return ResponseEntity.ok(leaveRequest);
    }
    
    @PutMapping("/access/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update parent access")
    public ResponseEntity<ParentAccess> updateParentAccess(
            @PathVariable Long id,
            @Valid @RequestBody ParentAccess parentAccess) {
        ParentAccess updatedAccess = parentPortalService.updateParentAccess(id, parentAccess);
        return ResponseEntity.ok(updatedAccess);
    }
    
    @DeleteMapping("/access/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete parent access")
    public ResponseEntity<Map<String, String>> deleteParentAccess(@PathVariable Long id) {
        parentPortalService.deleteParentAccess(id);
        return ResponseEntity.ok(Map.of("message", "Parent access deleted successfully"));
    }
}
