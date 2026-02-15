package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotNull(message = "From date is required")
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;
    
    @NotNull(message = "To date is required")
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
    
    @NotBlank(message = "Reason is required")
    @Column(name = "reason", nullable = false, length = 500)
    private String reason;
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @Column(name = "parent_consent")
    private Boolean parentConsent = false;
    
    @Column(name = "parent_contact")
    private String parentContact;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
