package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.*;
import com.hostel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentPortalService {
    
    private final ParentAccessRepository parentAccessRepository;
    private final StudentRepository studentRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final AttendanceRepository attendanceRepository;
    private final ComplaintRepository complaintRepository;
    private final EmergencyContactRepository emergencyContactRepository;
    private final NotificationRepository notificationRepository;
    private final RoomAllocationRepository roomAllocationRepository;
    private final FeeService feeService;
    private final AttendanceService attendanceService;
    
    @Transactional
    public ParentAccess createParentAccess(ParentAccess parentAccess) {
        Student student = studentRepository.findById(parentAccess.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + parentAccess.getStudentId()));
        
        parentAccess.setStudentName(student.getName());
        parentAccess.setIsActive(true);
        
        return parentAccessRepository.save(parentAccess);
    }
    
    public List<ParentAccess> getParentAccessByParentUserId(Long parentUserId) {
        return parentAccessRepository.findByParentUserId(parentUserId);
    }
    
    public List<Student> getStudentsByParentUserId(Long parentUserId) {
        List<ParentAccess> accessList = parentAccessRepository.findByParentUserId(parentUserId);
        return accessList.stream()
                .map(access -> studentRepository.findById(access.getStudentId()).orElse(null))
                .filter(student -> student != null)
                .collect(Collectors.toList());
    }
    
    public ParentDashboard getParentDashboard(Long parentUserId, Long studentId) {
        // Verify parent has access to this student
        ParentAccess access = parentAccessRepository.findByParentUserIdAndStudentId(parentUserId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent access not found"));
        
        if (!access.getIsActive()) {
            throw new IllegalStateException("Parent access is not active");
        }
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        
        ParentDashboard dashboard = new ParentDashboard();
        dashboard.setStudent(student);
        
        // Room allocation
        RoomAllocation currentAllocation = roomAllocationRepository.findByStudentIdAndStatus(studentId, "ACTIVE")
                .orElse(null);
        dashboard.setCurrentRoomAllocation(currentAllocation);
        
        // Fee information
        if (access.getCanViewFees()) {
            List<FeePayment> pendingFees = feePaymentRepository.findByStudentIdAndStatus(studentId, "PENDING");
            dashboard.setPendingFees(pendingFees);
            dashboard.setTotalPending(feeService.getTotalPendingByStudent(studentId));
            dashboard.setTotalPaid(feeService.getTotalPaidByStudent(studentId));
        }
        
        // Leave requests
        if (access.getCanApproveLeave()) {
            List<LeaveRequest> allLeaves = leaveRequestRepository.findByStudentId(studentId);
            List<LeaveRequest> pendingLeaves = allLeaves.stream()
                    .filter(l -> "PENDING".equals(l.getStatus()))
                    .collect(Collectors.toList());
            List<LeaveRequest> recentLeaves = allLeaves.stream()
                    .limit(5)
                    .collect(Collectors.toList());
            dashboard.setPendingLeaveRequests(pendingLeaves);
            dashboard.setRecentLeaveRequests(recentLeaves);
        }
        
        // Attendance
        if (access.getCanViewAttendance()) {
            Long attendancePercentage = attendanceService.getAttendancePercentage(studentId);
            List<Attendance> recentAttendance = attendanceRepository.findByStudentId(studentId).stream()
                    .limit(10)
                    .collect(Collectors.toList());
            dashboard.setAttendancePercentage(attendancePercentage);
            dashboard.setRecentAttendance(recentAttendance);
        }
        
        // Complaints
        if (access.getCanViewComplaints()) {
            List<Complaint> recentComplaints = complaintRepository.findByStudentId(studentId).stream()
                    .limit(5)
                    .collect(Collectors.toList());
            dashboard.setRecentComplaints(recentComplaints);
        }
        
        // Emergency contacts
        List<EmergencyContact> emergencyContacts = emergencyContactRepository.findByStudentIdOrderByPriorityAsc(studentId);
        dashboard.setEmergencyContacts(emergencyContacts);
        
        // Notifications
        List<Notification> recentNotifications = notificationRepository.findByTargetUserId(studentId).stream()
                .limit(5)
                .collect(Collectors.toList());
        dashboard.setRecentNotifications(recentNotifications);
        
        return dashboard;
    }
    
    @Transactional
    public LeaveRequest approveLeaveRequest(Long parentUserId, Long leaveRequestId, String remarks) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveRequestId));
        
        // Verify parent has access
        ParentAccess access = parentAccessRepository.findByParentUserIdAndStudentId(parentUserId, leaveRequest.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent access not found"));
        
        if (!access.getCanApproveLeave()) {
            throw new IllegalStateException("Parent does not have permission to approve leave requests");
        }
        
        leaveRequest.setParentConsent(true);
        leaveRequest.setRemarks(remarks);
        
        return leaveRequestRepository.save(leaveRequest);
    }
    
    @Transactional
    public ParentAccess updateParentAccess(Long id, ParentAccess accessDetails) {
        ParentAccess access = parentAccessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent access not found with id: " + id));
        
        access.setCanApproveLeave(accessDetails.getCanApproveLeave());
        access.setCanViewFees(accessDetails.getCanViewFees());
        access.setCanViewAttendance(accessDetails.getCanViewAttendance());
        access.setCanViewComplaints(accessDetails.getCanViewComplaints());
        access.setIsActive(accessDetails.getIsActive());
        
        return parentAccessRepository.save(access);
    }
    
    @Transactional
    public void deleteParentAccess(Long id) {
        ParentAccess access = parentAccessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent access not found with id: " + id));
        parentAccessRepository.delete(access);
    }
}
