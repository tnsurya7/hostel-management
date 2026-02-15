package com.hostel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDashboard {
    
    private Student student;
    private RoomAllocation currentRoomAllocation;
    private List<FeePayment> pendingFees;
    private Double totalPending;
    private Double totalPaid;
    private List<LeaveRequest> pendingLeaveRequests;
    private List<LeaveRequest> recentLeaveRequests;
    private Long attendancePercentage;
    private List<Attendance> recentAttendance;
    private List<Complaint> recentComplaints;
    private List<EmergencyContact> emergencyContacts;
    private List<Notification> recentNotifications;
}
