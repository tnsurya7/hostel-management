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
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Room number is required")
    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;
    
    @NotBlank(message = "Room type is required")
    @Column(name = "room_type", nullable = false)
    private String roomType; // SINGLE, DOUBLE, TRIPLE, QUAD
    
    @NotNull(message = "Capacity is required")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
    
    @Column(name = "floor")
    private Integer floor;
    
    @Column(name = "block")
    private String block;
    
    @Column(name = "current_occupancy")
    private Integer currentOccupancy = 0;
    
    @Column(name = "status")
    private String status = "AVAILABLE"; // AVAILABLE, OCCUPIED, FULL, MAINTENANCE, UNAVAILABLE
    
    @Column(name = "rent_amount")
    private Double rentAmount;
    
    @Column(name = "has_ac")
    private Boolean hasAc = false;
    
    @Column(name = "has_attached_bathroom")
    private Boolean hasAttachedBathroom = false;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
