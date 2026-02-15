package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "parent_access")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentAccess {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Parent user ID is required")
    @Column(name = "parent_user_id", nullable = false)
    private Long parentUserId;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @Column(name = "relationship")
    private String relationship; // FATHER, MOTHER, GUARDIAN
    
    @Column(name = "can_approve_leave")
    private Boolean canApproveLeave = true;
    
    @Column(name = "can_view_fees")
    private Boolean canViewFees = true;
    
    @Column(name = "can_view_attendance")
    private Boolean canViewAttendance = true;
    
    @Column(name = "can_view_complaints")
    private Boolean canViewComplaints = true;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
