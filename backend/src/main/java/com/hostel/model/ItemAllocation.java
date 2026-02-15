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
@Table(name = "item_allocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAllocation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Item ID is required")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @Column(name = "item_name")
    private String itemName;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @Column(name = "quantity")
    private Integer quantity = 1;
    
    @Column(name = "allocated_date")
    private LocalDate allocatedDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, RETURNED, DAMAGED, LOST
    
    @Column(name = "condition_on_return")
    private String conditionOnReturn;
    
    @Column(name = "damage_charge")
    private Double damageCharge = 0.0;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
