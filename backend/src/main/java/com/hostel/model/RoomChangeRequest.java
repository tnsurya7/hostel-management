package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_change_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomChangeRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotNull(message = "Current room ID is required")
    @Column(name = "current_room_id", nullable = false)
    private Long currentRoomId;
    
    @Column(name = "current_room_number")
    private String currentRoomNumber;
    
    @Column(name = "requested_room_id")
    private Long requestedRoomId;
    
    @Column(name = "requested_room_number")
    private String requestedRoomNumber;
    
    @NotBlank(message = "Reason is required")
    @Column(name = "reason", nullable = false, length = 500)
    private String reason;
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, COMPLETED
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
