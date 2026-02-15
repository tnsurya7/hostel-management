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
@Table(name = "laundry_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaundryRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "item_count")
    private Integer itemCount;
    
    @Column(name = "service_type")
    private String serviceType; // WASH, IRON, DRY_CLEAN, WASH_AND_IRON
    
    @Column(name = "pickup_date")
    private LocalDate pickupDate;
    
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    
    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, PICKED_UP, IN_PROCESS, READY, DELIVERED
    
    @Column(name = "charges")
    private Double charges = 0.0;
    
    @Column(name = "payment_status")
    private String paymentStatus = "UNPAID"; // UNPAID, PAID
    
    @Column(name = "special_instructions", length = 500)
    private String specialInstructions;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
