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
@Table(name = "room_maintenance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMaintenance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Room ID is required")
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @NotBlank(message = "Issue description is required")
    @Column(name = "issue", nullable = false, length = 500)
    private String issue;
    
    @Column(name = "category")
    private String category; // ELECTRICAL, PLUMBING, FURNITURE, CLEANING, OTHER
    
    @Column(name = "priority")
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT
    
    @Column(name = "status")
    private String status = "REPORTED"; // REPORTED, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
    
    @Column(name = "reported_by")
    private String reportedBy;
    
    @Column(name = "assigned_to")
    private String assignedTo;
    
    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;
    
    @Column(name = "completed_date")
    private LocalDateTime completedDate;
    
    @Column(name = "cost")
    private Double cost;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
