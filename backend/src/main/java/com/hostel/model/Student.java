package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "fees_paid")
    private Boolean feesPaid = false;
    
    @Column(name = "admission_date")
    private LocalDate admissionDate;
    
    // Parent Details
    @Column(name = "parent_name")
    private String parentName;
    
    @Column(name = "parent_phone")
    private String parentPhone;
    
    @Column(name = "parent_email")
    private String parentEmail;
    
    @Column(name = "emergency_contact")
    private String emergencyContact;
    
    // Additional Details
    @Column(name = "blood_group")
    private String bloodGroup;
    
    @Column(name = "course")
    private String course;
    
    @Column(name = "year_of_study")
    private String yearOfStudy;
    
    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, INACTIVE, ON_LEAVE
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
