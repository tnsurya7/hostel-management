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
@Table(name = "visitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotBlank(message = "Visitor name is required")
    @Column(name = "visitor_name", nullable = false)
    private String visitorName;
    
    @Column(name = "visitor_phone")
    private String visitorPhone;
    
    @Column(name = "visitor_id_proof")
    private String visitorIdProof;
    
    @Column(name = "relationship")
    private String relationship;
    
    @Column(name = "purpose")
    private String purpose;
    
    @Column(name = "entry_time")
    private LocalDateTime entryTime;
    
    @Column(name = "exit_time")
    private LocalDateTime exitTime;
    
    @Column(name = "pass_number")
    private String passNumber;
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, IN_PROGRESS, COMPLETED
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
