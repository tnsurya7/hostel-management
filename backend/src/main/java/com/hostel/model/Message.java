package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Sender ID is required")
    @Column(name = "sender_id", nullable = false)
    private Long senderId;
    
    @Column(name = "sender_name")
    private String senderName;
    
    @NotNull(message = "Receiver ID is required")
    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;
    
    @Column(name = "receiver_name")
    private String receiverName;
    
    @NotBlank(message = "Message content is required")
    @Column(name = "content", nullable = false, length = 2000)
    private String content;
    
    @Column(name = "message_type")
    private String messageType = "DIRECT"; // DIRECT, GROUP, BROADCAST
    
    @Column(name = "group_id")
    private Long groupId;
    
    @Column(name = "is_read")
    private Boolean isRead = false;
    
    @Column(name = "read_at")
    private LocalDateTime readAt;
    
    @Column(name = "priority")
    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
