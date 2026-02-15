package com.hostel.controller;

import com.hostel.model.Notification;
import com.hostel.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Notification Management", description = "APIs for managing notifications and announcements")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create a new notification")
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }
    
    @PostMapping("/broadcast")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Broadcast notification to all users")
    public ResponseEntity<Notification> broadcastNotification(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String message = request.get("message");
        String type = request.get("type");
        String targetAudience = request.get("targetAudience");
        String sentBy = request.get("sentBy");
        
        Notification notification = notificationService.broadcastNotification(title, message, type, targetAudience, sentBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
    
    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notifications by user ID")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications by user ID")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    
    @PatchMapping("/{id}/read")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(notification);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete notification")
    public ResponseEntity<Map<String, String>> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(Map.of("message", "Notification deleted successfully"));
    }
}
