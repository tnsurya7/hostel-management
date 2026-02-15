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
@Table(name = "fee_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Fee type name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name; // HOSTEL_FEE, MESS_FEE, SECURITY_DEPOSIT, MAINTENANCE_FEE, LAUNDRY_FEE
    
    @NotNull(message = "Amount is required")
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @Column(name = "frequency")
    private String frequency; // MONTHLY, SEMESTER, YEARLY, ONE_TIME
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "is_mandatory")
    private Boolean isMandatory = true;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
