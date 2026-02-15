package com.hostel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboard {
    
    private Student student;
    private RoomAllocation currentRoomAllocation;
    private List<FeePayment> pendingFees;
    private List<FeePayment> recentPayments;
    private Double totalPending;
    private Double totalPaid;
    private List<LeaveRequest> recentLeaveRequests;
    private List<Complaint> activeComplaints;
    private List<GatePass> activeGatePasses;
    private Long attendancePercentage;
    private List<Notification> unreadNotifications;
    private List<Announcement> recentAnnouncements;
    private List<LaundryRequest> activeLaundryRequests;
}
