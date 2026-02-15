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
@Table(name = "emergency_contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotBlank(message = "Contact name is required")
    @Column(name = "contact_name", nullable = false)
    private String contactName;
    
    @NotBlank(message = "Contact phone is required")
    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;
    
    @Column(name = "relationship")
    private String relationship;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
    
    @Column(name = "priority")
    private Integer priority = 1;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
