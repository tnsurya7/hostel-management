package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.Announcement;
import com.hostel.model.Message;
import com.hostel.repository.AnnouncementRepository;
import com.hostel.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunicationService {
    
    private final MessageRepository messageRepository;
    private final AnnouncementRepository announcementRepository;
    
    // ============ MESSAGE OPERATIONS ============
    
    @Transactional
    public Message sendMessage(Message message) {
        message.setIsRead(false);
        return messageRepository.save(message);
    }
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
    }
    
    public List<Message> getMessagesBySenderId(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }
    
    public List<Message> getMessagesByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }
    
    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverIdAndIsRead(receiverId, false);
    }
    
    @Transactional
    public Message markAsRead(Long id) {
        Message message = getMessageById(id);
        message.setIsRead(true);
        message.setReadAt(LocalDateTime.now());
        return messageRepository.save(message);
    }
    
    @Transactional
    public void deleteMessage(Long id) {
        Message message = getMessageById(id);
        messageRepository.delete(message);
    }
    
    // ============ ANNOUNCEMENT OPERATIONS ============
    
    @Transactional
    public Announcement createAnnouncement(Announcement announcement) {
        announcement.setIsActive(true);
        return announcementRepository.save(announcement);
    }
    
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }
    
    public List<Announcement> getActiveAnnouncements() {
        return announcementRepository.findByIsActive(true);
    }
    
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
    }
    
    public List<Announcement> getAnnouncementsByCategory(String category) {
        return announcementRepository.findByCategory(category);
    }
    
    @Transactional
    public Announcement updateAnnouncement(Long id, Announcement announcementDetails) {
        Announcement announcement = getAnnouncementById(id);
        announcement.setTitle(announcementDetails.getTitle());
        announcement.setContent(announcementDetails.getContent());
        announcement.setCategory(announcementDetails.getCategory());
        announcement.setTargetAudience(announcementDetails.getTargetAudience());
        announcement.setPriority(announcementDetails.getPriority());
        announcement.setExpiresAt(announcementDetails.getExpiresAt());
        return announcementRepository.save(announcement);
    }
    
    @Transactional
    public void deleteAnnouncement(Long id) {
        Announcement announcement = getAnnouncementById(id);
        announcementRepository.delete(announcement);
    }
}
