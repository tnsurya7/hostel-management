package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.*;
import com.hostel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentPortalService {
    
    private final StudentRepository studentRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final ComplaintRepository complaintRepository;
    private final GatePassRepository gatePassRepository;
    private final NotificationRepository notificationRepository;
    private final AnnouncementRepository announcementRepository;
    private final LaundryRequestRepository laundryRequestRepository;
    private final RoomAllocationRepository roomAllocationRepository;
    private final FeeService feeService;
    private final AttendanceService attendanceService;
    
    public StudentDashboard getStudentDashboard(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        
        StudentDashboard dashboard = new StudentDashboard();
        dashboard.setStudent(student);
        
        // Current room allocation
        RoomAllocation currentAllocation = roomAllocationRepository.findByStudentIdAndStatus(studentId, "ACTIVE")
                .orElse(null);
        dashboard.setCurrentRoomAllocation(currentAllocation);
        
        // Fee information
        List<FeePayment> pendingFees = feePaymentRepository.findByStudentIdAndStatus(studentId, "PENDING");
        List<FeePayment> recentPayments = feePaymentRepository.findByStudentIdAndStatus(studentId, "PAID").stream()
                .limit(5)
                .collect(Collectors.toList());
        dashboard.setPendingFees(pendingFees);
        dashboard.setRecentPayments(recentPayments);
        dashboard.setTotalPending(feeService.getTotalPendingByStudent(studentId));
        dashboard.setTotalPaid(feeService.getTotalPaidByStudent(studentId));
        
        // Leave requests
        List<LeaveRequest> recentLeaveRequests = leaveRequestRepository.findByStudentId(studentId).stream()
                .limit(5)
                .collect(Collectors.toList());
        dashboard.setRecentLeaveRequests(recentLeaveRequests);
        
        // Complaints
        List<Complaint> activeComplaints = complaintRepository.findByStudentId(studentId).stream()
                .filter(c -> !"CLOSED".equals(c.getStatus()) && !"RESOLVED".equals(c.getStatus()))
                .collect(Collectors.toList());
        dashboard.setActiveComplaints(activeComplaints);
        
        // Gate passes
        List<GatePass> activeGatePasses = gatePassRepository.findByStudentIdAndStatus(studentId, "APPROVED");
        dashboard.setActiveGatePasses(activeGatePasses);
        
        // Attendance
        Long attendancePercentage = attendanceService.getAttendancePercentage(studentId);
        dashboard.setAttendancePercentage(attendancePercentage);
        
        // Notifications
        List<Notification> unreadNotifications = notificationRepository.findByTargetUserIdAndIsRead(studentId, false);
        dashboard.setUnreadNotifications(unreadNotifications);
        
        // Announcements
        List<Announcement> recentAnnouncements = announcementRepository.findByIsActive(true).stream()
                .limit(5)
                .collect(Collectors.toList());
        dashboard.setRecentAnnouncements(recentAnnouncements);
        
        // Laundry requests
        List<LaundryRequest> activeLaundryRequests = laundryRequestRepository.findByStudentId(studentId).stream()
                .filter(l -> !"DELIVERED".equals(l.getStatus()))
                .collect(Collectors.toList());
        dashboard.setActiveLaundryRequests(activeLaundryRequests);
        
        return dashboard;
    }
    
    public Student getStudentProfile(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
    }
    
    public List<FeePayment> getStudentFees(Long studentId) {
        return feePaymentRepository.findByStudentId(studentId);
    }
    
    public List<LeaveRequest> getStudentLeaveRequests(Long studentId) {
        return leaveRequestRepository.findByStudentId(studentId);
    }
    
    public List<Complaint> getStudentComplaints(Long studentId) {
        return complaintRepository.findByStudentId(studentId);
    }
    
    public List<GatePass> getStudentGatePasses(Long studentId) {
        return gatePassRepository.findByStudentId(studentId);
    }
}
