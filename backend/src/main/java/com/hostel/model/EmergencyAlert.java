package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "student_id")
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotBlank(message = "Alert type is required")
    @Column(name = "alert_type", nullable = false)
    private String alertType; // MEDICAL, FIRE, SECURITY, ACCIDENT, SOS, OTHER
    
    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "severity")
    private String severity = "MEDIUM"; // LOW, MEDIUM, HIGH, CRITICAL
    
    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, ACKNOWLEDGED, RESOLVED, CLOSED
    
    @Column(name = "reported_by")
    private String reportedBy;
    
    @Column(name = "responded_by")
    private String respondedBy;
    
    @Column(name = "response_time")
    private LocalDateTime responseTime;
    
    @Column(name = "resolution_time")
    private LocalDateTime resolutionTime;
    
    @Column(name = "action_taken", length = 1000)
    private String actionTaken;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
