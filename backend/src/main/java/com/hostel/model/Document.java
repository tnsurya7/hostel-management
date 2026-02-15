package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotBlank(message = "Document name is required")
    @Column(name = "document_name", nullable = false)
    private String documentName;
    
    @Column(name = "document_type")
    private String documentType; // ID_PROOF, PHOTO, CERTIFICATE, MEDICAL, OTHER
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "file_url")
    private String fileUrl;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "mime_type")
    private String mimeType;
    
    @Column(name = "verification_status")
    private String verificationStatus = "PENDING"; // PENDING, VERIFIED, REJECTED
    
    @Column(name = "verified_by")
    private String verifiedBy;
    
    @Column(name = "verified_date")
    private LocalDateTime verifiedDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Column(name = "is_expired")
    private Boolean isExpired = false;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
