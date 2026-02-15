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
@Table(name = "beds")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bed {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Room ID is required")
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    
    @NotBlank(message = "Bed number is required")
    @Column(name = "bed_number", nullable = false)
    private String bedNumber;
    
    @Column(name = "is_occupied")
    private Boolean isOccupied = false;
    
    @Column(name = "student_id")
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @Column(name = "status")
    private String status = "AVAILABLE"; // AVAILABLE, OCCUPIED, MAINTENANCE
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
