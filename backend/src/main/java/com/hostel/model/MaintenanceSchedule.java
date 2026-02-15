package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", length = 1000)
    private String description;
    
    @Column(name = "maintenance_type")
    private String maintenanceType; // PREVENTIVE, SCHEDULED, ROUTINE, INSPECTION
    
    @Column(name = "category")
    private String category; // ELECTRICAL, PLUMBING, HVAC, STRUCTURAL, CLEANING, OTHER
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;
    
    @Column(name = "completed_date")
    private LocalDate completedDate;
    
    @Column(name = "frequency")
    private String frequency; // DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY, ONE_TIME
    
    @Column(name = "assigned_to")
    private String assignedTo;
    
    @Column(name = "status")
    private String status = "SCHEDULED"; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, POSTPONED
    
    @Column(name = "priority")
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT
    
    @Column(name = "estimated_cost")
    private Double estimatedCost;
    
    @Column(name = "actual_cost")
    private Double actualCost;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
