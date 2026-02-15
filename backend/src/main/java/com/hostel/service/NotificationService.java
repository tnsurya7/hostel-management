package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.Notification;
import com.hostel.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    @Transactional
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public Notification broadcastNotification(String title, String message, String type, String targetAudience, String sentBy) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setTargetAudience(targetAudience);
        notification.setSentBy(sentBy);
        notification.setIsRead(false);
        
        return notificationRepository.save(notification);
    }
    
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }
    
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByTargetUserId(userId);
    }
    
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByTargetUserIdAndIsRead(userId, false);
    }
    
    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = getNotificationById(id);
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
}
