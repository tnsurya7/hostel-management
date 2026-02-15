package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotBlank(message = "Content is required")
    @Column(name = "content", nullable = false, length = 2000)
    private String content;
    
    @Column(name = "category")
    private String category; // GENERAL, EVENT, NOTICE, ALERT, HOLIDAY
    
    @Column(name = "target_audience")
    private String targetAudience = "ALL"; // ALL, STUDENTS, ADMINS, WARDENS, SPECIFIC_ROOM, SPECIFIC_FLOOR
    
    @Column(name = "priority")
    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "posted_by")
    private String postedBy;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
