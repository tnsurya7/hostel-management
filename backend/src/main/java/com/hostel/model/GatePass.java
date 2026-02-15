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
@Table(name = "gate_passes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatePass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotBlank(message = "Purpose is required")
    @Column(name = "purpose", nullable = false, length = 500)
    private String purpose;
    
    @Column(name = "pass_type")
    private String passType; // DAY_PASS, NIGHT_PASS, WEEKEND_PASS, EMERGENCY
    
    @NotNull(message = "From time is required")
    @Column(name = "from_time", nullable = false)
    private LocalDateTime fromTime;
    
    @NotNull(message = "To time is required")
    @Column(name = "to_time", nullable = false)
    private LocalDateTime toTime;
    
    @Column(name = "actual_return_time")
    private LocalDateTime actualReturnTime;
    
    @Column(name = "pass_number")
    private String passNumber;
    
    @Column(name = "qr_code")
    private String qrCode;
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, ACTIVE, COMPLETED, OVERDUE
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    
    @Column(name = "late_return")
    private Boolean lateReturn = false;
    
    @Column(name = "penalty_amount")
    private Double penaltyAmount = 0.0;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
