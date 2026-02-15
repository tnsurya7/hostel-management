package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotBlank(message = "Message is required")
    @Column(name = "message", nullable = false, length = 1000)
    private String message;
    
    @Column(name = "type")
    private String type; // INFO, WARNING, ALERT, ANNOUNCEMENT, EVENT
    
    @Column(name = "target_audience")
    private String targetAudience; // ALL, STUDENTS, ADMINS, WARDENS, SPECIFIC_STUDENT
    
    @Column(name = "target_user_id")
    private Long targetUserId;
    
    @Column(name = "is_read")
    private Boolean isRead = false;
    
    @Column(name = "priority")
    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT
    
    @Column(name = "sent_by")
    private String sentBy;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
