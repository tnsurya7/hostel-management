package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_allocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomAllocation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotNull(message = "Room ID is required")
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "bed_id")
    private Long bedId;
    
    @Column(name = "bed_number")
    private String bedNumber;
    
    @NotNull(message = "From date is required")
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;
    
    @Column(name = "to_date")
    private LocalDate toDate;
    
    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, CANCELLED
    
    @Column(name = "allocated_by")
    private String allocatedBy;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
