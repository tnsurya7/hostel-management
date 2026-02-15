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
@Table(name = "complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
    
    @Column(name = "category")
    private String category; // ROOM, MESS, MAINTENANCE, SECURITY, CLEANLINESS, OTHER
    
    @Column(name = "priority")
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, CRITICAL
    
    @Column(name = "status")
    private String status = "OPEN"; // OPEN, IN_PROGRESS, RESOLVED, CLOSED, REJECTED
    
    @Column(name = "assigned_to")
    private String assignedTo;
    
    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;
    
    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;
    
    @Column(name = "resolution", length = 1000)
    private String resolution;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
