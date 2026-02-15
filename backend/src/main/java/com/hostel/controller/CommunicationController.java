package com.hostel.controller;

import com.hostel.model.Announcement;
import com.hostel.model.Message;
import com.hostel.service.CommunicationService;
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
@RequestMapping("/api/communication")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Communication Hub", description = "APIs for messaging and announcements")
public class CommunicationController {
    
    private final CommunicationService communicationService;
    
    // ============ MESSAGE ENDPOINTS ============
    
    @PostMapping("/messages")
    @Operation(summary = "Send a message")
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody Message message) {
        Message sentMessage = communicationService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }
    
    @GetMapping("/messages")
    @Operation(summary = "Get all messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = communicationService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/{id}")
    @Operation(summary = "Get message by ID")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = communicationService.getMessageById(id);
        return ResponseEntity.ok(message);
    }
    
    @GetMapping("/messages/sender/{senderId}")
    @Operation(summary = "Get messages by sender ID")
    public ResponseEntity<List<Message>> getMessagesBySenderId(@PathVariable Long senderId) {
        List<Message> messages = communicationService.getMessagesBySenderId(senderId);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/receiver/{receiverId}")
    @Operation(summary = "Get messages by receiver ID")
    public ResponseEntity<List<Message>> getMessagesByReceiverId(@PathVariable Long receiverId) {
        List<Message> messages = communicationService.getMessagesByReceiverId(receiverId);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/receiver/{receiverId}/unread")
    @Operation(summary = "Get unread messages")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Long receiverId) {
        List<Message> messages = communicationService.getUnreadMessages(receiverId);
        return ResponseEntity.ok(messages);
    }
    
    @PatchMapping("/messages/{id}/read")
    @Operation(summary = "Mark message as read")
    public ResponseEntity<Message> markAsRead(@PathVariable Long id) {
        Message message = communicationService.markAsRead(id);
        return ResponseEntity.ok(message);
    }
    
    @DeleteMapping("/messages/{id}")
    @Operation(summary = "Delete message")
    public ResponseEntity<Map<String, String>> deleteMessage(@PathVariable Long id) {
        communicationService.deleteMessage(id);
        return ResponseEntity.ok(Map.of("message", "Message deleted successfully"));
    }
    
    // ============ ANNOUNCEMENT ENDPOINTS ============
    
    @PostMapping("/announcements")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create announcement")
    public ResponseEntity<Announcement> createAnnouncement(@Valid @RequestBody Announcement announcement) {
        Announcement createdAnnouncement = communicationService.createAnnouncement(announcement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }
    
    @GetMapping("/announcements")
    @Operation(summary = "Get all announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = communicationService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }
    
    @GetMapping("/announcements/active")
    @Operation(summary = "Get active announcements")
    public ResponseEntity<List<Announcement>> getActiveAnnouncements() {
        List<Announcement> announcements = communicationService.getActiveAnnouncements();
        return ResponseEntity.ok(announcements);
    }
    
    @GetMapping("/announcements/{id}")
    @Operation(summary = "Get announcement by ID")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id) {
        Announcement announcement = communicationService.getAnnouncementById(id);
        return ResponseEntity.ok(announcement);
    }
    
    @GetMapping("/announcements/category/{category}")
    @Operation(summary = "Get announcements by category")
    public ResponseEntity<List<Announcement>> getAnnouncementsByCategory(@PathVariable String category) {
        List<Announcement> announcements = communicationService.getAnnouncementsByCategory(category);
        return ResponseEntity.ok(announcements);
    }
    
    @PutMapping("/announcements/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update announcement")
    public ResponseEntity<Announcement> updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody Announcement announcement) {
        Announcement updatedAnnouncement = communicationService.updateAnnouncement(id, announcement);
        return ResponseEntity.ok(updatedAnnouncement);
    }
    
    @DeleteMapping("/announcements/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete announcement")
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@PathVariable Long id) {
        communicationService.deleteAnnouncement(id);
        return ResponseEntity.ok(Map.of("message", "Announcement deleted successfully"));
    }
}
